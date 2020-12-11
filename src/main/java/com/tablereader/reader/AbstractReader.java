package com.tablereader.reader;

import com.tablereader.model.TableData;
import com.tablereader.model.TableFile;

import java.io.File;
import java.io.IOException;

public abstract class AbstractReader {

    public abstract TableData read(File file) throws IOException, RuntimeException;

    public abstract TableData read(File file, String encoding) throws IOException, RuntimeException;

    public abstract TableFile readTableFile(File file) throws IOException, RuntimeException;
}
