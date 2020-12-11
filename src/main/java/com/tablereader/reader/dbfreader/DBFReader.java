package com.tablereader.reader.dbfreader;

import com.tablereader.file.FileUtils;
import com.tablereader.model.Field;
import com.tablereader.model.FieldHandler;
import com.tablereader.model.TableData;
import com.tablereader.model.TableFile;
import com.tablereader.reader.AbstractReader;
import org.jamel.dbf.DbfReader;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBFReader extends AbstractReader {
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String DEFAULT_CHARSET = "cp866";
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    public List<DBFField> getHeadFields(DbfReader dbfReader) {
        List<DBFField> dbfFieldList = new ArrayList<>();
        if (dbfReader != null) {
            for (int i = 0; i < dbfReader.getHeader().getFieldsCount(); i++) {
                dbfFieldList.add(new DBFField(i, dbfReader.getHeader().getField(i).getName(),
                        DBFFieldType.valueOf(getDBFFieldTypeName(dbfReader, i)),
                        dbfReader.getHeader().getField(i).getFieldLength()));
            }
        }
        return dbfFieldList;
    }

    private String getDBFFieldTypeName(DbfReader dbfReader, int i) {
        return String.valueOf((char) (dbfReader.getHeader().getField(i).getDataType().byteValue));
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
                    switch (DBFFieldType.valueOf(getDBFFieldTypeName(dbfReader, i))) {

                        case C:
                            elem = new String((byte[]) row[i], Charset.forName(charset));

                            break;
                        case D:
                            String d = simpleDateFormat.format((Date) row[i]);
                            elem = d.equals("06.10.17793") ? "" : d;
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

    public List<Field> getFieldList(DbfReader dbfReader) {
        return getFieldList(getHeadFields(dbfReader));
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
        return TableData.builder()
                .fields(getFieldList(dbfReader))
                .data(getRecordsList(dbfReader, encoding))
                .build();
    }

    @Override
    public TableFile readTableFile(File file) {
        if (file == null) {
            return null;
        }
        String encoding = FileUtils.getFileEncodingOrDefault(file, DEFAULT_CHARSET);
        TableData tableData = read(file, encoding);

        return TableFile.builder()
                .file(file)
                .tableData(tableData)
                .encoding(encoding)
                .extension(FileUtils.getFileExtension(file))
                .build();
    }
}
