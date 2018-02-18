package com.tablereader.model;

/**
 * Created by AnGo on 05.03.2017.
 */
public class Field {
    private int id;
    private String name;
    private String fieldType;
    private int length;


    public Field() {
    }

    public Field(int id, String name, String fieldType, int length) {
        this.id = id;
        this.name = name;
        this.fieldType = fieldType;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Field{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", fieldType=").append(fieldType);
        sb.append(", length=").append(length);
        sb.append('}');
        return sb.toString();
    }

}
