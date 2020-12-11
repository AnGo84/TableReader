package com.tablereader.reader;

public enum AllowedFileType {
    DBF("dbf"), XLS("xls"), XLSX("xlsx");

    private String type;

    AllowedFileType(String type) {
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
