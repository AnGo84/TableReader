package com.tablereader.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class XLSXExportFile extends AbstractExportFile {
    @Override
    public void write(ExportData exportData) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        int rowNum = 0;

        for (Object[] objects : exportData.getTableData().getData()) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : objects) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(exportData.getFile());) {
            workbook.write(outputStream);
            workbook.close();
        }
    }

}
