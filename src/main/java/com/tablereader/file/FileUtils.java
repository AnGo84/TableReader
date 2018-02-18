package com.tablereader.file;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {
    public static String getFileEncodingSimple(File file) throws IOException {
        String encoding = null;
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(file))) {
            encoding = isr.getEncoding();
        }
        return encoding;
    }

    public static String getFileEncoding(File file) throws IOException {
        byte[] buf = new byte[4096];
        FileInputStream fis = new FileInputStream(file);

        UniversalDetector detector = new UniversalDetector(null);
        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }

        detector.dataEnd();

        String encoding = detector.getDetectedCharset();
        detector.reset();
        return encoding;
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    public static String getFileNameWithOutExt(String fileName) {
        return fileName.replaceFirst("[.][^.]+$", "");
    }
}
