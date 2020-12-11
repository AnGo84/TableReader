package com.tablereader.file;

import com.tablereader.AppTestUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    public void whenGetFileEncodingSimpleReturnResult() throws IOException {
        File file = new File(AppTestUtils.TEST_FILES_PATH + "/files/text.txt");
        String encoding = FileUtils.getFileEncodingSimple(file);
        assertTrue(file.exists());
        assertEquals("UTF8", encoding);
    }

    @Test
    public void whenGetFileEncodingSimpleThrowException() {
        assertThrows(NullPointerException.class, () -> FileUtils.getFileEncodingSimple(null));
        File wrongFile = new File("/wrong.txt");
        assertFalse(wrongFile.exists());
        assertThrows(IOException.class, () -> FileUtils.getFileEncodingSimple(wrongFile));
    }

    @Test
    public void whenGetFileEncodingReturnResult() throws IOException {
        File file = new File(AppTestUtils.TEST_FILES_PATH + "/files/text.txt");
        assertTrue(file.exists());
        String encoding = FileUtils.getFileEncoding(file);
        assertEquals(null, encoding);
    }

    @Test
    public void whenGetFileEncodingThrowException() {
        assertThrows(NullPointerException.class, () -> FileUtils.getFileEncoding(null));
        File wrongFile = new File("/wrong.txt");
        assertFalse(wrongFile.exists());
        assertThrows(IOException.class, () -> FileUtils.getFileEncoding(wrongFile));
    }

    @Test
    public void whenGetFileEncodingOrDefaultReturnResult() {
        File file = new File(AppTestUtils.TEST_FILES_PATH + "/files/text.txt");
        assertTrue(file.exists());
        String encoding = FileUtils.getFileEncodingOrDefault(file, null);
        assertEquals(null, encoding);
        encoding = FileUtils.getFileEncodingOrDefault(file, "default");
        assertEquals("default", encoding);

        File wrongFile = new File("/wrong.txt");
        assertFalse(wrongFile.exists());
        assertNull(FileUtils.getFileEncodingOrDefault(wrongFile, null));
        assertEquals("default", FileUtils.getFileEncodingOrDefault(wrongFile, "default"));
    }

    @Test
    public void whenGetFileEncodingOrDefaultThrowException() {
        assertThrows(NullPointerException.class, () -> FileUtils.getFileEncodingOrDefault(null, null));
        assertThrows(NullPointerException.class, () -> FileUtils.getFileEncodingOrDefault(null, "default"));
    }

    @Test
    public void whenGetFileExtensionReturnResult() throws IOException {
        File file = new File(AppTestUtils.TEST_FILES_PATH + "/files/text.txt");
        assertTrue(file.exists());
        assertEquals("txt", FileUtils.getFileExtension(file));

        File fileWithoutExtension = new File(AppTestUtils.TEST_FILES_PATH + "/files/text_without_extension");
        assertTrue(file.exists());
        assertEquals("", FileUtils.getFileExtension(fileWithoutExtension));

        File wrongFile = new File("/wrong.txt");
        assertFalse(wrongFile.exists());
        assertEquals("txt", FileUtils.getFileExtension(wrongFile));
        wrongFile = new File("/wrong");
        assertFalse(wrongFile.exists());
        assertEquals("", FileUtils.getFileExtension(wrongFile));
    }

    @Test
    public void whenGetFileExtensionThrowException() {
        assertThrows(NullPointerException.class, () -> FileUtils.getFileExtension(null));
    }

    @Test
    public void whenGetFileNameWithOutExtReturnResult() {
        assertEquals("", FileUtils.getFileNameWithOutExt(""));
        assertEquals("fileName", FileUtils.getFileNameWithOutExt("fileName"));
        assertEquals("fileName", FileUtils.getFileNameWithOutExt("fileName.extension"));
        assertEquals("fileName.add", FileUtils.getFileNameWithOutExt("fileName.add.extension"));
    }

    @Test
    public void whenGetFileNameWithOutExtThrowException() {
        assertThrows(NullPointerException.class, () -> FileUtils.getFileNameWithOutExt(null));
    }
}