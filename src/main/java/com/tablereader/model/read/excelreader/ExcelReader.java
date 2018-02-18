package com.tablereader.model.read.excelreader;

import com.tablereader.file.FileUtils;
import com.tablereader.model.read.AbstractReader;
import com.tablereader.model.TableData;
import com.tablereader.model.TableFile;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;

public class ExcelReader extends AbstractReader {
    @Override
    public TableData read(File file) throws IOException {
        TableData tableData = read(file, null);
        return tableData;
    }

    @Override
    public TableData read(File file, String encoding) throws IOException {
        if (file == null) {
            return null;
        }
        String fileExtension = FileUtils.getFileExtension(file);
        ExcelHandler excelHandler = new ExcelHandler();

        Workbook workbook = excelHandler.getWorkbook(file, fileExtension);

        TableData tableData = excelHandler.getTableData(workbook);
        return tableData;
    }

    @Override
    public TableFile readTableFile(File file) throws IOException {
        if (file == null) {
            return null;
        }
        TableFile tableFile = new TableFile();
        String fileExtension = FileUtils.getFileExtension(file);

        tableFile.setExtension(fileExtension);

        String encoding;
        try {
            encoding = FileUtils.getFileEncoding(file);
        } catch (IOException e) {
            encoding = "unknown";
        }
        tableFile.setEncoding(encoding);
        tableFile.setFile(file);

        ExcelHandler excelHandler = new ExcelHandler();
        Workbook workbook = excelHandler.getWorkbook(file, fileExtension);

        TableData tableData = excelHandler.getTableData(workbook);

        workbook.close();
        tableFile.setTableData(tableData);

        return tableFile;
    }

}
