package com.tablereader.reader.dbfreader;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DBFField {
    private int id;
    private String name;
    private DBFFieldType fieldType;
    private int length;

    public DBFField(int id, String name, DBFFieldType fieldType, int length) {
        this.id = id;
        this.name = name;
        this.fieldType = fieldType;
        this.length = length;
    }

}
