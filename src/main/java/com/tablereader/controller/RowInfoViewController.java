package com.tablereader.controller;

import com.tablereader.model.Field;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;

public class RowInfoViewController {
    @FXML
    private TextArea textAreaRecordInfo;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void initInfoDialog(List<Field> fields, String[] strings) {
        if (fields != null && strings != null) {
            textAreaRecordInfo.setText("");

            for (int i = 0; i < strings.length; i++) {
                textAreaRecordInfo.appendText(String.format("%s : %s", fields.get(i).getName(), strings[i]));
                textAreaRecordInfo.appendText("\n");
            }
        }
    }

    @FXML
    private void initialize() {
        textAreaRecordInfo.appendText("Record did not select");
    }

}
