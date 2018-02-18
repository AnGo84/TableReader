package com.tablereader.model;

import com.tablereader.model.read.dbfreader.DBFField;

public class FieldHandler {
    public static Field getField(DBFField dbfField){
        if (dbfField==null){
            return null;
        }
        Field field = new Field();
        field.setId(dbfField.getId());
        field.setName(dbfField.getName());
        field.setFieldType(dbfField.getFieldType().getType());
        field.setLength(dbfField.getLength());
        return field;
    }
}
