package com.tablereader.model;

import lombok.Builder;
import lombok.Data;

import java.io.File;

@Data
@Builder
public class TableFile {
    private File file;
    private String extension;
    private String encoding;
    private TableData tableData;

}
