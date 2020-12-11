package com.tablereader.export;

import com.tablereader.model.Field;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TXTExportFile extends AbstractExportFile {

    @Override
    public void write(ExportData exportData) throws IOException {
        if (exportData == null) {
            throw new IOException("Data not found");
        }
        if (exportData.getFile() == null) {
            throw new IOException("File not found");
        }
        try (FileWriter fileWriter = new FileWriter(exportData.getFile())) {

            if (exportData.getTableData() != null) {
                fileWriter.write(stringArrayToString(exportData.getTableData().getFields()) + "\r\n");

                for (String[] rec : exportData.getTableData().getData()) {
                    fileWriter.write(stringArrayToString(rec) + "\r\n");
                }
            }
        }
    }

    private String stringArrayToString(String[] strings) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]).append(" | ");
        }
        return sb.toString();
    }

    private String stringArrayToString(List<Field> fields) {
        final StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            sb.append(field.getName()).append(" | ");
        }

        return sb.toString();
    }
}
