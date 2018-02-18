package com.tablereader.controller;

import com.tablereader.model.Field;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by AnGo on 07.03.2017.
 */
public class RowInfoViewController {
    @FXML
    private TextArea textAreaRecordInfo;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void initInfoDialog(List<Field> dbfFields, String[] strings) {
        if (dbfFields != null && strings != null) {
            textAreaRecordInfo.setText("");
//            int max = 0;
//            for (DBFField dbfField : dbfFields) {
//                if (dbfField.getName().length() > max)
//                    max = dbfField.getName().length();
//            }

            for (int i = 0; i < strings.length; i++) {
//                textAreaRecordInfo.appendText(String.format("%" + max + "s : %s", dbfFields.get(i).getName(), strings[i]));
                textAreaRecordInfo.appendText(String.format("%s : %s", dbfFields.get(i).getName(), strings[i]));
                textAreaRecordInfo.appendText("\n");
            }
        }
    }

    @FXML
    private void initialize() {
        textAreaRecordInfo.appendText("Record did not select");
    }

}
