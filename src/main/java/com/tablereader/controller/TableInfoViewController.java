package com.tablereader.controller;

import com.tablereader.model.Field;
import com.tablereader.model.TableFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * Created by AnGo on 06.03.2017.
 */
public class TableInfoViewController {
    @FXML
    private Label labelFile;
    @FXML
    private Label labelRecords;
    @FXML
    private Label labelFields;@FXML
    private Label labelEncoding;

    @FXML
    private TableView tableFields;

    @FXML
    private TableColumn<Field, Integer> idColumn;
    @FXML
    private TableColumn<Field, String> nameColumn;
    @FXML
    private TableColumn<Field, String> fieldTypeColumn;
    @FXML
    private TableColumn<Field, Integer> lengthColumn;

    private Stage dialogStage;

    private TableFile tableFile;

    private ObservableList<Field> observableArrayList = FXCollections.observableArrayList();


    public void initInfoDialog(TableFile tableFile) {
        this.tableFields = tableFields;

        labelFile.setText(tableFile.getFile().getName());
        labelFields.setText(String.valueOf(tableFile.getTableData().getFields().size()));
        labelRecords.setText(String.valueOf(tableFile.getTableData().getData().size()));
        labelEncoding.setText(String.valueOf(tableFile.getEncoding()));

        observableArrayList.addAll(tableFile.getTableData().getFields());
        tableFields.setItems(observableArrayList);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Field, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Field, String>("name"));
        fieldTypeColumn.setCellValueFactory(new PropertyValueFactory<Field, String>("fieldType"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<Field, Integer>("length"));
    }

    public void onButtonOk(ActionEvent actionEvent) {
        dialogStage.close();
    }
}
