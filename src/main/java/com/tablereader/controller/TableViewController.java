package com.tablereader.controller;

import com.tablereader.MainApp;
import com.tablereader.fx.ImageResources;
import com.tablereader.model.Field;
import com.tablereader.model.TableData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TableViewController {
    private static final Logger logger = LogManager.getLogger(TableViewController.class);
    private static final String NOT_OPEN_FILE = "File not open";
    String filterText;
    @FXML
    private TableView<String[]> tableView = new TableView<>();
    @FXML
    private TabPane tabPaneTable;
    @FXML
    private GridPane gridPaneRecord;
    @FXML
    private Button buttonFirst;
    @FXML
    private Button buttonPrior;
    @FXML
    private Button buttonNext;
    @FXML
    private Button buttonLast;
    @FXML
    private Button buttonRefresh;
    @FXML
    private Button buttonViewRow;
    @FXML
    private Button buttonFilter;

    @FXML
    private AnchorPane anchorPaneFilterInfo;
    @FXML
    private Label labelFilterInfo;

    private MainApp mainApp;
    private ObservableList<String[]> observableList;
    private TableData tableData;
    private List<Field> fields;
    private List<String[]> recordsList;
    private List<String[]> filteredList;

    private String[] rowFields;
    private String[] filters;
    private TextField[] textFields;

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setObservableList(ObservableList<String[]> observableList) {
        this.observableList = observableList;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public TableData getTableData() {
        return tableData;
    }

    public void setTableData(TableData tableData) {
        this.tableData = tableData;
    }

    public String[] getFilters() {
        return filters;
    }

    public void setFilters(String[] filters) {
        this.filters = filters;
    }

    public List<String[]> getRecordsList() {
        return recordsList;
    }

    public void setRecordsList(List<String[]> recordsList) {
        this.recordsList = recordsList;
    }

    public List<String[]> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<String[]> filteredList) {
        this.filteredList = filteredList;
    }

    @FXML
    private void initialize() {
        prepareToNewFile();

        initButtonsIcons();
        initButtonsToolTip();

        /*setMenuItemDefaultState();*/

        elementDisableStates(true);

        labelFilterInfo.setVisible(true);
        labelFilterInfo.setText(NOT_OPEN_FILE);

        anchorPaneFilterInfo.managedProperty().bind(anchorPaneFilterInfo.visibleProperty());

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showRowFields(newValue));

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    onButtonViewRow(new ActionEvent());
                }
            }
        });

    }

    private void elementDisableStates(boolean hasData) {
        buttonFirst.setDisable(hasData);
        buttonPrior.setDisable(hasData);
        buttonNext.setDisable(hasData);
        buttonLast.setDisable(hasData);
        buttonRefresh.setDisable(hasData);

        buttonFilter.setDisable(hasData);
    }

    private void initButtonsToolTip() {
        buttonViewRow.setTooltip(new Tooltip("View Row's details of current record"));
        buttonFilter.setTooltip(new Tooltip("Show filter dialog"));

        buttonFirst.setTooltip(new Tooltip("Go to first record"));
        buttonPrior.setTooltip(new Tooltip("Go to prior record"));
        buttonNext.setTooltip(new Tooltip("Go to next record"));
        buttonLast.setTooltip(new Tooltip("Go to last record"));
        buttonRefresh.setTooltip(new Tooltip("Refresh table data"));
    }

    private void initButtonsIcons() {
        buttonViewRow.setGraphic(new ImageView(ImageResources.getButtonView()));
        buttonFilter.setGraphic(new ImageView(ImageResources.getButtonFilter()));

        buttonFirst.setGraphic(new ImageView(ImageResources.getButtonFirst()));
        buttonPrior.setGraphic(new ImageView(ImageResources.getButtonPrior()));
        buttonNext.setGraphic(new ImageView(ImageResources.getButtonNext()));
        buttonLast.setGraphic(new ImageView(ImageResources.getButtonLast()));
        buttonRefresh.setGraphic(new ImageView(ImageResources.getButtonRefresh()));
    }

    private void fillTable(List<Field> fields, List<String[]> recordsList) {
        this.fields = fields;
        this.recordsList = recordsList;

        // Create Columns
        for (Field field : fields) {
            final int colNo = field.getId();
            TableColumn column = new TableColumn(field.getName());
            column.setMinWidth(field.getLength() > field.getName().length() ? field.getLength() : field.getName().length());
            //Mystery :)
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                                           @Override
                                           public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                                               return new SimpleStringProperty((p.getValue()[colNo]));
                                           }
                                       }
            );
            tableView.getColumns().add(column);
        }
        tableView.setItems(setTableViewDate(recordsList));
    }

    private void prepareToNewFile() {
        labelFilterInfo.setText("");
        filters = null;
        anchorPaneFilterInfo.setVisible(false);

        tableView.getColumns().removeAll(tableView.getColumns());
        tableView.getItems().removeAll(tableView.getItems());
        gridPaneRecord.getChildren().removeAll(gridPaneRecord.getChildren());

        buttonViewRow.setDisable(true);
        elementDisableStates(true);
    }

    public void fillTable() {
        prepareToNewFile();
        fillTable(tableData.getFields(), tableData.getData());
        initGridPaneRecord(tableData.getFields());

        elementDisableStates(!mainApp.tableHasData());
    }

    private ObservableList<String[]> setTableViewDate(List<String[]> recordsList) {
        observableList = FXCollections.observableArrayList();
        observableList.addAll(recordsList);
        return observableList;
    }

    public void initGridPaneRecord(List<Field> fields) {
        gridPaneRecord.getRowConstraints().removeAll(gridPaneRecord.getRowConstraints());
        if (fields != null && !fields.isEmpty()) {
            this.textFields = new TextField[fields.size()];
            for (Field field : fields) {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPrefHeight(40);
                gridPaneRecord.getRowConstraints().addAll(rowConst);
//            gridPane.setGridLinesVisible(true);
                Label label = new Label(field.getName());
                label.setPadding(new Insets(0, 0, 0, 10));
                gridPaneRecord.add(label, 0, field.getId());

                TextField textField = new TextField();
                textField.setEditable(false);

                textField.setId("field" + field.getName());
                gridPaneRecord.add(textField, 1, field.getId());
                this.textFields[field.getId()] = textField;
            }
        }
    }

    public void showRowFields(String[] rowFields) {
        if (this.fields != null && !this.fields.isEmpty()) {
            buttonViewRow.setDisable(false);

            if (rowFields != null && rowFields.length > 0) {
                this.rowFields = rowFields;

                for (Field field : this.fields) {
                    textFields[field.getId()].setText(rowFields[field.getId()]);
                }
            } else {
                buttonViewRow.setDisable(true);
                for (Field field : this.fields) {
                    textFields[field.getId()].setText("");
                }
            }
        }

    }

    public void onButtonFirst(ActionEvent actionEvent) {
        tableView.getSelectionModel().selectFirst();
        tableView.scrollTo(tableView.getSelectionModel().getSelectedIndex());
    }

    public void onButtonPrior(ActionEvent actionEvent) {
        tableView.getSelectionModel().selectPrevious();
        tableView.scrollTo(tableView.getSelectionModel().getSelectedIndex());
    }

    public void onButtonNext(ActionEvent actionEvent) {
        tableView.getSelectionModel().selectNext();
        tableView.scrollTo(tableView.getSelectionModel().getSelectedIndex());
    }

    public void onButtonLast(ActionEvent actionEvent) {
        tableView.getSelectionModel().selectLast();
        tableView.scrollTo(tableView.getSelectionModel().getSelectedIndex());
    }

    public void onButtonRefresh(ActionEvent actionEvent) {
        tableView.refresh();
    }

    public void onButtonViewRow(ActionEvent actionEvent) {
        if (tableView.getItems() != null && !tableView.getItems().isEmpty()) {
            mainApp.showRowInfoView(fields, tableView.getSelectionModel().getSelectedItem());
        }
    }

    public void onButtonFilter(ActionEvent actionEvent) {
        if (tableData != null) {
            filterText = "";

            if ((filters = mainApp.showTableFilterView(fields, filters)) != null) {

                filteredList = new ArrayList<>();

                for (String[] strings : recordsList) {

                    if (isContainedFilter(strings, filters)) {
                        filteredList.add(strings);
                    }
                }

                if (!(filterText = getFilterText(filters)).equals("")) {
                    tabPaneTable.getSelectionModel().select(0);
                }
                tableView.setItems(setTableViewDate(filteredList));
            } else {
                tableView.setItems(setTableViewDate(recordsList));
            }
            labelFilterInfo.setText(filterText);
            anchorPaneFilterInfo.setVisible(!filterText.equals(""));
        }

    }

    private boolean isContainedFilter(String[] record, String[] filter) {
        int recCount = 0;
        for (int i = 0; i < record.length; i++) {
            if (filter[i] != null && !filter[i].equals("") && !record[i].contains(filter[i])) {
                break;
            }
            recCount++;
        }
        return (recCount == record.length ? true : false);
    }

    private String getFilterText(String[] strings) {
        if (strings != null) {
            String filterText = "";
            for (int i = 0; i < strings.length; i++) {
                if (strings[i] != null && !strings[i].equals("")) {
                    filterText += " " + fields.get(i).getName() + " = " + strings[i] + ";";
                }
            }
            return !filterText.equals("") ? "Filter:" + filterText : "";
        }
        return "";
    }

}
