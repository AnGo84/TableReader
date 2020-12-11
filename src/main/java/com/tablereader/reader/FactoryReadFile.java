package com.tablereader.reader;

import com.tablereader.file.FileUtils;
import com.tablereader.reader.dbfreader.DBFReader;
import com.tablereader.reader.excelreader.ExcelReader;

import java.io.File;

public class FactoryReadFile {
    public AbstractReader getReader(File file) {
        AbstractReader reader = null;
        String fileExtension = FileUtils.getFileExtension(file);

        if (fileExtension.equalsIgnoreCase(AllowedFileType.DBF.getType())) {
            reader = new DBFReader();
        } else if (fileExtension.equalsIgnoreCase(AllowedFileType.XLS.getType())
                || fileExtension.equalsIgnoreCase(AllowedFileType.XLSX.getType())) {
            reader = new ExcelReader();
        }
        return reader;
    }

}
