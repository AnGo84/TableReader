package com.tablereader.model.read;


import com.tablereader.model.read.dbfreader.DBFReader;
import com.tablereader.model.read.excelreader.ExcelReader;

import java.io.File;

public class FactoryReadFile {
    public AbstractReader getReader(File file) {
        AbstractReader reader = null;
        if (getFileExtension(file).toUpperCase().equals("DBF")) {
            reader = new DBFReader();
        } else if (getFileExtension(file).toUpperCase().equals("XLS") || getFileExtension(file).toUpperCase().equals("XLSX")) {
            reader = new ExcelReader();
        }
        return reader;
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }
}
