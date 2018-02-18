package com.tablereader.model;

import java.util.List;

public class TableData {
    private List<Field> fields;
    private List<String[]> data;

    public TableData() {
    }

    public TableData(List<Field> fields, List<String[]> data) {
        this.data = data;
        this.fields = fields;
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
