package com.tablereader.model;

import com.tablereader.AppTestUtils;
import com.tablereader.reader.dbfreader.DBFField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FieldHandlerTest {

    @Test
    public void whenGetFieldReturnResult() {
        assertNull(FieldHandler.getField(null));

        DBFField expectedDBFField = AppTestUtils.getDBFField();
        Field field = FieldHandler.getField(expectedDBFField);

        assertEquals(expectedDBFField.getId(), field.getId());
        assertEquals(expectedDBFField.getName(), field.getName());
        assertEquals(expectedDBFField.getFieldType().getType(), field.getFieldType());
        assertEquals(expectedDBFField.getLength(), field.getLength());
    }
}