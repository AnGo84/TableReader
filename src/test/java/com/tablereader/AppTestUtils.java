package com.tablereader;

import com.tablereader.reader.dbfreader.DBFField;
import com.tablereader.reader.dbfreader.DBFFieldType;

public class AppTestUtils {
    public static final String TEST_FILES_PATH = "src/test/resources";

    public static DBFField getDBFField() {
        DBFField dbfField = DBFField.builder()
                .id(1)
                .fieldType(DBFFieldType.N)
                .name("number")
                .length(10)
                .build();
        return dbfField;
    }
}
