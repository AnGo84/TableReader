package com.tablereader.export;

import java.io.IOException;

public abstract class AbstractExportFile {

    public abstract void write(ExportData exportData) throws IOException;

}
