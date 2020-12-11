package com.tablereader.fx;

public enum RecordDialogType {
    FILTER("Filter"),
    EDIT("Edit");

    private String dialogName;

    RecordDialogType(String dialogName) {
        this.dialogName = dialogName;
    }

    public String getDialogName() {
        return dialogName;
    }
}
