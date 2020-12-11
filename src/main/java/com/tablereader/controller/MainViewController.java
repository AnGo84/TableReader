package com.tablereader.controller;

import com.tablereader.MainApp;
import com.tablereader.export.AbstractExportFile;
import com.tablereader.export.ExportData;
import com.tablereader.export.ExportFileType;
import com.tablereader.export.FactoryExportFile;
import com.tablereader.file.FileUtils;
import com.tablereader.fx.DialogText;
import com.tablereader.fx.Dialogs;
import com.tablereader.fx.ImageResources;
import com.tablereader.model.TableData;
import com.tablereader.model.TableFile;
import com.tablereader.properties.PreferencesHandler;
import com.tablereader.reader.AbstractReader;
import com.tablereader.reader.AllowedFileType;
import com.tablereader.reader.FactoryReadFile;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainViewController {
    private static final Logger logger = LogManager.getLogger(MainViewController.class);
    private static final String FILTER_DESCRIPTION = "%s files (*.%s)";
    private static final String FILTER_EXTENSION = "*.%s";
    private final List<FileChooser.ExtensionFilter> fileFilters = new ArrayList<>();

    private MainApp mainApp;
    @FXML
    private Menu menuExport;
    @FXML
    private Menu menuView;

    @FXML
    private MenuItem menuItemOpen;
    @FXML
    private MenuItem menuItemExit;
    @FXML
    private MenuItem menuItemFilter;
    @FXML
    private MenuItem menuItemTableInfo;
    @FXML
    private MenuItem menuItemTXTExport;
    @FXML
    private MenuItem menuItemXLSExport;
    @FXML
    private MenuItem menuItemXLSXExport;
    @FXML
    private MenuItem menuItemSQLExport;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        initButtonsIcons();
        setMenuItemDefaultState();
        initExtensionFilters();

        elementDisableStates(true);
    }

    private void elementDisableStates(boolean hasDate) {
        menuExport.setDisable(hasDate);
        menuView.setDisable(hasDate);
    }

    private void initButtonsIcons() {
        menuItemOpen.setGraphic(new ImageView(ImageResources.getMenuItemOpen()));
        menuItemExit.setGraphic(new ImageView(ImageResources.getMenuItemExit()));
        menuItemFilter.setGraphic(new ImageView(ImageResources.getButtonFilter()));
        menuItemTableInfo.setGraphic(new ImageView(ImageResources.getTableInformationIcon()));

        menuItemTXTExport.setGraphic(new ImageView(ImageResources.getTxt24FileIcon()));
        menuItemXLSExport.setGraphic(new ImageView(ImageResources.getXls24FileIcon()));
        menuItemXLSXExport.setGraphic(new ImageView(ImageResources.getXlsx24FileIcon()));
        menuItemSQLExport.setGraphic(new ImageView(ImageResources.getSql24FileIcon()));

    }

    private void initExtensionFilters() {
        List<String> extensions = new ArrayList<>();
        for (AllowedFileType type : AllowedFileType.values()) {
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    String.format(FILTER_DESCRIPTION, type.getType().toUpperCase(), type.getType()), String.format(FILTER_EXTENSION, type.getType()));
            fileFilters.add(extFilter);
            extensions.add(String.format(FILTER_EXTENSION, type.getType()));
        }
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All", extensions);
        fileFilters.add(extFilter);
    }

    private void setMenuItemDefaultState() {
        menuItemOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        menuItemExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        menuItemFilter.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
        menuItemTableInfo.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));

        //menuItemProperties.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
    }

    public void onMenuItemExit(ActionEvent actionEvent) {
        if (Dialogs.showConfirmDialog(new DialogText("Close application", "Application will be closed", "Are you agree?"), logger))
            Platform.exit();
    }

    public void onMenuItemOpen(ActionEvent actionEvent) {

        File file = Dialogs.openFileDialog(PreferencesHandler.getPreferenceFilePath(mainApp.getClass(),
                PreferencesHandler.LAST_FILE_PATH), fileFilters, mainApp.getPrimaryStage());
        if (file != null) {
            AbstractReader reader = new FactoryReadFile().getReader(file);
            try {
                TableFile tableFile = reader.readTableFile(file);
                mainApp.setTableFile(tableFile);

                elementDisableStates(!mainApp.tableHasData());

                PreferencesHandler.setPreferenceFilePath(file, mainApp.getClass(), PreferencesHandler.LAST_FILE_PATH);
                logger.info("Open file " + file.getAbsolutePath());
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
                Dialogs.showErrorDialog(e, new DialogText("File reading", "", "Can't read file"), logger);
            }

        }
    }

    public void onMenuItemFilter(ActionEvent actionEvent) {
        mainApp.getTableViewController().onButtonFilter(actionEvent);
    }

    public void onMenuItemTableInfo(ActionEvent actionEvent) {
        if (mainApp.getTableFile() != null && mainApp.getTableFile().getFile() != null) {
            mainApp.showTableInfoView(mainApp.getTableFile());
        }

    }

    private ExportData getExportData(File file) {
        TableData tableData;
        if (mainApp.getTableViewController().getFilters() != null) {
            tableData = TableData.builder()
                    .fields(mainApp.getTableFile().getTableData().getFields())
                    .data(mainApp.getTableViewController().getFilteredList())
                    .build();
        } else {
            tableData = mainApp.getTableFile().getTableData();
        }
        ExportData exportData = ExportData.builder()
                .tableData(tableData)
                .file(file)
                .build();
        return exportData;
    }

    private String getExportFileName(String name) {
        if (mainApp.getTableViewController().getFilters() != null) {
            return name + "Filtered";
        } else {
            return name;
        }
    }

    private void exportData(ExportFileType exportFileType) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(exportFileType + " files",
                "*." + exportFileType.getType().toLowerCase());
        String exportFileName = getExportFileName(FileUtils.getFileNameWithOutExt(mainApp.getTableFile().getFile().getName()));
        File file = Dialogs.saveFileDialog(exportFileName, PreferencesHandler.getPreferenceFilePath(mainApp.getClass(),
                PreferencesHandler.LAST_FILE_PATH), extFilter, mainApp.getPrimaryStage());

        if (file != null) {
            AbstractExportFile writer = new FactoryExportFile().getWriter(file);

            ExportData exportData = getExportData(file);
            try {
                writer.write(exportData);
                Dialogs.showMessage(Alert.AlertType.INFORMATION, new DialogText("File writing", "File '" + file.getAbsolutePath(),
                        "File '" + file.getName() + "' saved successfully"), logger);
            } catch (IOException e) {
                e.printStackTrace();
                Dialogs.showErrorDialog(e, new DialogText("File writing", "", "Can't write file" + file.getName()), logger);
            }
        }
    }

    public void onMenuItemTXTExport(ActionEvent actionEvent) {
        exportData(ExportFileType.TXT);
    }

    public void onMenuItemXLSExport(ActionEvent actionEvent) {
        exportData(ExportFileType.XLS);
    }

    public void onMenuItemXLSXExport(ActionEvent actionEvent) {
        exportData(ExportFileType.XLSX);
    }

    public void onMenuItemSQLExport(ActionEvent actionEvent) {
        exportData(ExportFileType.SQL);
    }
}
