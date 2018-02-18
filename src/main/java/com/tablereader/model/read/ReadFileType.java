package com.tablereader.model.read;

/**
 * Created by AnGo on 27.01.2018.
 */
public enum ReadFileType {
    DBF("dbf"), XLS("xls"), XLSX("xlsx");

    private String type;

    ReadFileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
