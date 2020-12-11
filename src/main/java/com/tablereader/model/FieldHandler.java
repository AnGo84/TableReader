package com.tablereader.model;

import com.tablereader.reader.dbfreader.DBFField;

public class FieldHandler {
    public static Field getField(DBFField dbfField) {
        if (dbfField == null) {
            return null;
        }
        return Field.builder()
                .id(dbfField.getId())
                .name(dbfField.getName())
                .fieldType(dbfField.getFieldType().getType())
                .length(dbfField.getLength())
                .build();
    }
}
