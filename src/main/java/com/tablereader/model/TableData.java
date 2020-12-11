package com.tablereader.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TableData {
    private List<Field> fields;
    private List<String[]> data;
}
