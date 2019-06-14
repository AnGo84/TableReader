package com.tablereader.model.export;

import com.tablereader.file.FileUtils;
import com.tablereader.model.Field;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SQLExportFile extends AbstractExportFile {
    private static final String INSERT_TEMP = "INSERT INTO %s (%s) VALUES (%s);";

    @Override
    public void write(ExportData exportData) throws IOException {
        if (exportData == null) {
            throw new IOException("Data not found");
        }
        if (exportData.getFile() == null) {
            throw new IOException("File not found");
        }

        String tableName = FileUtils.getFileNameWithOutExt(exportData.getFile().getName());
        String fieldsString = getFieldsString(exportData.getTableData().getFields());
        try (FileWriter fileWriter = new FileWriter(exportData.getFile())) {
            for (String[] strings : exportData.getTableData().getData()) {
                String dataString = getDataString(strings);
                String out = String.format(INSERT_TEMP, tableName, fieldsString, dataString);
                //System.out.println(out);
                fileWriter.write(out + "\r\n");
            }
        }

    }

    private String getFieldsString(List<Field> fields) {
        String fieldsString = "";
        for (Field field : fields) {
            String fieldName = getFieldName(field);
            if (fieldsString.equals("")) {
                fieldsString = fieldName;
            } else {
                fieldsString += "," + fieldName;
            }
        }
        return fieldsString;
    }

    private String getDataString(String[] data) {
        String dataString = "";
        for (String sData : data) {
            if(sData!=null) {
                String s = getShieldingString(sData.trim());
                if (dataString.equals("")) {
                    dataString = "\'" + s + "\'";
                } else {
                    dataString += ",\'" + s + "\'";
                }
            }
        }
        return dataString;
    }

    private String getFieldName(Field field) {
        if (field==null || field.getName()==null)
        {
            return "";
        }
        String[] s = field.getName().split(" ");
        return s[0];
    }

    private String getShieldingString(String inString) {
        String outString = inString.replaceAll("'", "''").replaceAll("\"", "\"").replaceAll("&", "'||'&'||'");
        return outString;
    }
}
