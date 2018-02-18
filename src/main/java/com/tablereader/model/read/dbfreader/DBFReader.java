package com.tablereader.model.read.dbfreader;

import com.tablereader.file.FileUtils;


import com.tablereader.model.Field;
import com.tablereader.model.FieldHandler;
import com.tablereader.model.TableData;
import com.tablereader.model.TableFile;
import com.tablereader.model.read.AbstractReader;
import org.jamel.dbf.DbfReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AnGo on 04.03.2017.
 */
public class DBFReader extends AbstractReader {
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String DEFAULT_CHARSET = "cp866";
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    public List<DBFField> getHeadFields(DbfReader dbfReader) {
        List<DBFField> dbfFieldList = new ArrayList<>();
        if (dbfReader != null) {
            for (int i = 0; i < dbfReader.getHeader().getFieldsCount(); i++) {
                dbfFieldList.add(new DBFField(i, dbfReader.getHeader().getField(i).getName(),
                        DBFFieldType.valueOf(String.valueOf((char) (dbfReader.getHeader().getField(i).getDataType()))),
                        dbfReader.getHeader().getField(i).getFieldLength()));
            }
        }
        return dbfFieldList;
    }

    public List<String[]> getRecordsList(DbfReader dbfReader, String encoding) {
        List<String[]> recordsList = new ArrayList<>();
        String charset = (encoding == null || encoding.equals("")) ? DEFAULT_CHARSET : encoding;

        if (dbfReader != null) {
            Object[] row;
            while ((row = dbfReader.nextRecord()) != null) {
                String[] record = new String[dbfReader.getHeader().getFieldsCount()];
                for (int i = 0; i < row.length; i++) {
                    String elem = "";
                    switch (DBFFieldType.valueOf(String.valueOf((char) dbfReader.getHeader().getField(i).getDataType()))) {

                        case C:
                            //elem = new String((byte[]) row[i], Charset.forName("cp866"));

                            elem = new String((byte[]) row[i], Charset.forName(charset));

                            break;
                        case D:
                            String d = simpleDateFormat.format((Date) row[i]);
                            elem = d.equals("06.10.17793") ? "" : d;
                            // elem = new String(String.valueOf(row[i]));
                            break;
                        default:
                            elem = String.valueOf(row[i]);
                    }
                    record[i] = elem;
                }
                recordsList.add(record);
            }
        }
        return recordsList;
    }

    public List<Field> getFieldList(List<DBFField> dbfFields) {
        if (dbfFields == null || dbfFields.isEmpty()) {
            return null;
        }
        List<Field> fields = new ArrayList<>();
        for (DBFField dbfField : dbfFields) {
            fields.add(FieldHandler.getField(dbfField));
        }
        return fields;
    }

    @Override
    public TableData read(File file) {
        TableData tableData = read(file, null);
        return tableData;
    }

    @Override
    public TableData read(File file, String encoding) {
        if (file == null) {
            return null;
        }

        DbfReader dbfReader = new DbfReader(file);
        TableData tableData = new TableData(getFieldList(getHeadFields(dbfReader)), getRecordsList(dbfReader, encoding));
        return tableData;
    }

    @Override
    public TableFile readTableFile(File file) {
        if (file == null) {
            return null;
        }
        TableFile tableFile = new TableFile();
        tableFile.setExtension(FileUtils.getFileExtension(file));

        String encoding;
        try {
            encoding = FileUtils.getFileEncoding(file);

        } catch (IOException e) {
            encoding = DEFAULT_CHARSET;
        }
        tableFile.setEncoding(encoding);
        tableFile.setFile(file);

        TableData tableData = read(file, encoding);
        tableFile.setTableData(tableData);
        return tableFile;
    }
}
