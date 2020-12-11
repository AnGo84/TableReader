package com.tablereader.reader.excelreader;

import com.tablereader.file.FileUtils;
import com.tablereader.model.TableData;
import com.tablereader.model.TableFile;
import com.tablereader.reader.AbstractReader;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;

public class ExcelReader extends AbstractReader {
    private ExcelHandler excelHandler = new ExcelHandler();

    @Override
    public TableData read(File file) throws IOException {
        TableData tableData = read(file, null);
        return tableData;
    }

    @Override
    public TableData read(File file, String encoding) throws IOException, Error {
        if (file == null) {
            return null;
        }
        String fileExtension = FileUtils.getFileExtension(file);


        Workbook workbook = excelHandler.getWorkbook(file, fileExtension);

        TableData tableData = excelHandler.getTableData(workbook);
        return tableData;
    }

    @Override
    public TableFile readTableFile(File file) throws IOException {
        if (file == null) {
            return null;
        }
        String fileExtension = FileUtils.getFileExtension(file);

        return TableFile.builder()
                .file(file)
                .extension(fileExtension)
                .encoding(FileUtils.getFileEncodingOrDefault(file, "unknown"))
                .tableData(excelHandler.getTableData(file, fileExtension))
                .build();
    }

}
