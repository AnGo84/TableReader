package com.tablereader.model.read.dbfreader;

/**
 * Created by AnGo on 05.03.2017.
 */
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

    public int getId() {  return id;    }

    public String getName() {
        return name;
    }

    public DBFFieldType getFieldType() {
        return fieldType;
    }

    public int getLength() {
        return length;
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
