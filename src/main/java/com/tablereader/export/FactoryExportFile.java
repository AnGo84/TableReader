package com.tablereader.export;

import com.tablereader.file.FileUtils;

import java.io.File;

public class FactoryExportFile {
    public AbstractExportFile getWriter(File file) {
        if (file == null) {
            return null;
        }
        AbstractExportFile writer = null;
        if (FileUtils.getFileExtension(file).toUpperCase().equals(ExportFileType.TXT.toString())) {
            writer = new TXTExportFile();
        } else if (FileUtils.getFileExtension(file).toUpperCase().equals(ExportFileType.XLS.toString())) {
            writer = new XLSExportFile();
        } else if (FileUtils.getFileExtension(file).toUpperCase().equals(ExportFileType.XLSX.toString())) {
            writer = new XLSXExportFile();
        } else if (FileUtils.getFileExtension(file).toUpperCase().equals(ExportFileType.SQL.toString())) {
            writer = new SQLExportFile();
        } else {

        }
        return writer;
    }
}
