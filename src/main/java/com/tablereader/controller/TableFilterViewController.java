package com.tablereader.controller;

import com.tablereader.model.Field;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.List;

public class TableFilterViewController {
    @FXML
    private GridPane gridPane;

    private Stage dialogStage;

    private String[] filterString;

    private List<Field> fields;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public String[] getFilterString() {
        return filterString;
    }

    public void setFilterString(String[] filterString) {
        this.filterString = filterString;
    }

    public void initInfoDialog(List<Field> fields, String[] filter) {
        this.fields = fields;
        setFilterString(filter);
        gridPane.getRowConstraints().removeAll(gridPane.getRowConstraints());

        for (Field field : fields) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(40);
            gridPane.getRowConstraints().addAll(rowConst);
//            gridPane.setGridLinesVisible(true);
            Label label = new Label(field.getName());
            label.setPadding(new Insets(0, 0, 0, 10));
            gridPane.add(label, 0, field.getId());

            TextField textField = new TextField();
            textField.setId("field" + field.getName());
            if (filterString != null && !filterString[field.getId()].equals(""))
                textField.setText(filterString[field.getId()]);
            gridPane.add(textField, 1, field.getId());

        }
    }

    public void onActionButtonFindCancel(ActionEvent actionEvent) {
        filterString = null;
        dialogStage.close();
    }

    public void onActionButtonFindOk(ActionEvent actionEvent) {
        filterString = new String[fields.size()];
        for (Field dbfField : fields) {
            TextField textField = (TextField) gridPane.lookup("#field" + dbfField.getName());
            if (textField != null) {
                filterString[dbfField.getId()] = textField.getText();
            }
        }
        dialogStage.close();
    }
}
