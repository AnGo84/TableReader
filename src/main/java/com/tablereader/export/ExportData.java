package com.tablereader.export;

import com.tablereader.model.Field;
import com.tablereader.model.TableData;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.List;

@Data
@Builder
public class ExportData {
    private List<Field> fieldList;
    private TableData tableData;
    private File file;

}
