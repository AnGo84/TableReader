package com.tablereader.reader;

import com.tablereader.AppTestUtils;
import com.tablereader.reader.dbfreader.DBFReader;
import com.tablereader.reader.excelreader.ExcelReader;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FactoryReadFileTest {
    private FactoryReadFile factoryReadFile = new FactoryReadFile();

    @Test
    public void whenGetReaderReturnResult() {
        assertNull(factoryReadFile.getReader(new File("wrong_file")));

        File file = new File(AppTestUtils.TEST_FILES_PATH + "/files/text.txt");
        assertTrue(file.exists());
        AbstractReader reader = factoryReadFile.getReader(file);
        assertNull(reader);

        file = new File(AppTestUtils.TEST_FILES_PATH + "/files/empty_xlsx.xlsx");
        assertTrue(file.exists());
        reader = factoryReadFile.getReader(file);
        assertNotNull(reader);
        assertEquals(ExcelReader.class, reader.getClass());

        file = new File(AppTestUtils.TEST_FILES_PATH + "/files/empty_xls.xls");
        assertTrue(file.exists());
        reader = factoryReadFile.getReader(file);
        assertNotNull(reader);
        assertEquals(ExcelReader.class, reader.getClass());

        file = new File(AppTestUtils.TEST_FILES_PATH + "/files/empty_dbf.dbf");
        assertTrue(file.exists());
        reader = factoryReadFile.getReader(file);
        assertNotNull(reader);
        assertEquals(DBFReader.class, reader.getClass());
    }

    @Test
    public void whenGetReaderThrowNPE() {
        assertThrows(NullPointerException.class, () -> factoryReadFile.getReader(null));
    }

}