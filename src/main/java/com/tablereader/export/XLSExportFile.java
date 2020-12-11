package com.tablereader.export;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileOutputStream;
import java.io.IOException;

public class XLSExportFile extends AbstractExportFile {
    @Override
    public void write(ExportData exportData) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

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
