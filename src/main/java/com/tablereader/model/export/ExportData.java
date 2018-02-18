package com.tablereader.model.export;

import com.tablereader.model.Field;
import com.tablereader.model.TableData;

import java.io.File;
import java.util.List;

public class ExportData {
    private List<Field> fieldList;
    private TableData tableData;
    private File file;

    public ExportData() {
    }

    public ExportData(TableData tableData) {
        this.fieldList = tableData.getFields();
        this.tableData = tableData;
    }

    public ExportData(TableData tableData, List<Field> fieldList) {
        this.fieldList = (fieldList == null || fieldList.isEmpty()) ? tableData.getFields() : fieldList;
        this.tableData = tableData;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public TableData getTableData() {
        return tableData;
    }

    public void setTableData(TableData tableData) {
        this.tableData = tableData;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
