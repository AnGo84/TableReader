package com.tablereader.reader.dbfreader;

public enum DBFFieldType {
    C("character"), D("date"), F("float"), L("logic"), N("numeric");

    private String type;

    DBFFieldType(String c) {
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
