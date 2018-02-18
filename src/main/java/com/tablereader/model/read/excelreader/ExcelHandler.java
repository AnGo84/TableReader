package com.tablereader.model.read.excelreader;

import com.tablereader.model.Field;
import com.tablereader.model.TableData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHandler {

    public Workbook getWorkbook(File file, String fileExtension)
            throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;

        if (fileExtension.toUpperCase().equals("XLSX")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (fileExtension.toUpperCase().equals("XLS")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }


    public List<Field> getFieldList(Workbook workbook) {
        if (workbook == null) {
            return null;
        }
        List<Field> fields = new ArrayList<>();

        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int colNum = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                String cellValue = getCellStringValue(cell);
                System.out.print(cellValue);

                Field field = new Field();
                field.setId(colNum);
                //field.setName(cell.getStringCellValue());
                field.setName("Field_" + (colNum + 1));
                field.setFieldType(cell.getCellTypeEnum().name());
                field.setLength(50);

                colNum++;
                fields.add(field);
            }
            break;
        }
        return fields;
    }

    private String getCellStringValue(Cell cell) {
        String cellValue;
        switch (cell.getCellTypeEnum()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case BLANK:
                cellValue = "";
                break;
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case ERROR:
                cellValue = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    public List<String[]> getRowsList(Workbook workbook, int fieldsCount) {
        if (workbook == null) {
            return null;
        }

        List<String[]> rowsList = new ArrayList<>();

        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            String[] row = new String[fieldsCount];
            int colNum = 0;
            while (cellIterator.hasNext() && colNum < fieldsCount) {
                Cell cell = cellIterator.next();

                //String cellValue = getCellStringValue(cell);

                row[colNum] = cell.getStringCellValue();
                colNum++;
            }
            rowsList.add(row);
        }

        return rowsList;
    }

    public TableData getTableData(Workbook workbook) {
        List<Field> fieldList = getFieldList(workbook);
        List<String[]> rowsList = getRowsList(workbook, fieldList.size());
        return new TableData(fieldList, rowsList);
    }
}