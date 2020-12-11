package com.tablereader.reader.excelreader;

import com.tablereader.model.Field;
import com.tablereader.model.TableData;
import com.tablereader.reader.AllowedFileType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    public static final int DEFAULT_FIELD_LENGTH = 50;
    public static final String FIELD_NAME_PREFIX = "Field_";
    private static final Logger logger = LogManager.getLogger(ExcelHandler.class);

    public Workbook getWorkbook(File file, String fileExtension) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook;
        if (fileExtension.equalsIgnoreCase(AllowedFileType.XLSX.getType())) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (fileExtension.equalsIgnoreCase(AllowedFileType.XLS.getType())) {
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
                fields.add(getFieldFromCell(cellIterator.next(), colNum));
                colNum++;
            }
            break;
        }
        return fields;
    }

    private Field getFieldFromCell(Cell cell, int colNum) {
        Field field = Field.builder().id(colNum)
                .name(generateFieldName(colNum))
                .fieldType(cell.getCellType().name())
                .length(DEFAULT_FIELD_LENGTH)
                .build();
        return field;
    }

    private String generateFieldName(int colNum) {
        return FIELD_NAME_PREFIX + (colNum + 1);
    }

    public TableData getTableData(File file, String fileExtension) throws IOException {

        Workbook workbook = getWorkbook(file, fileExtension);

        TableData tableData = getTableData(workbook);

        workbook.close();
        return tableData;
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
                try {
                    row[colNum] = getCellStringValue(cell);
                }catch (Exception e){
                    logger.error("Error on colNum " + colNum + " for CELL " + cell.getAddress()+ " " + cell);
                }
                colNum++;
            }
            rowsList.add(row);
        }

        return rowsList;
    }

    public TableData getTableData(Workbook workbook) {
        List<Field> fieldList = getFieldList(workbook);
        List<String[]> rowsList = getRowsList(workbook, fieldList.size());
        return TableData.builder().fields(fieldList)
                .data(rowsList).build();
    }
}