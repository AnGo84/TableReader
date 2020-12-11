package com.tablereader.export;

public enum ExportFileType {
    TXT("TXT"), XLS("XLS"), XLSX("XLSX"), SQL("SQL");

    private String type;

    ExportFileType(String c) {
        this.type = c;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
