package com.tablereader.model.read;

import com.tablereader.model.TableData;
import com.tablereader.model.TableFile;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public abstract class AbstractReader {

    public abstract TableData read(File file) throws IOException;

    public abstract TableData read(File file, String encoding) throws IOException;

    public abstract TableFile readTableFile(File file) throws IOException;
}
