package com.tablereader;

import com.tablereader.controller.*;
import com.tablereader.fx.DialogText;
import com.tablereader.fx.Dialogs;
import com.tablereader.fx.ImageResources;
import com.tablereader.model.Field;
import com.tablereader.model.TableFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by AnGo on 19.01.2018.
 */
public class MainApp extends Application {
    private static final String APP_NAME = "TableReader";

    private static final Logger rootLogger = LogManager.getRootLogger();

    private Stage primaryStage;
    private BorderPane mainView;

    private TableViewController tableViewController;

    private TableFile tableFile;

    public static void main(String[] args) {
        launch(args);
    }

    public TableFile getTableFile() {
        return tableFile;
    }

    public void setTableFile(TableFile tableFile) {
        this.tableFile = tableFile;
        if (tableFile != null && tableFile.getFile() != null) {
            setAppTitle(tableFile.getFile().getAbsolutePath());
        }
        if (tableViewController != null) {

            tableViewController.setTableData(tableFile.getTableData());
            tableViewController.fillTable();
        }

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public TableViewController getTableViewController() {
        return tableViewController;
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle(APP_NAME);

        initMainView();
        showTableView();
    }


    public void initMainView() {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MainApp.class.getResource("/view/MainView.fxml"));

            mainView = loader.load();

            Scene scene = new Scene(mainView);
            primaryStage.setScene(scene);

            primaryStage.getIcons().add(ImageResources.getAppIcon());
            MainViewController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Dialogs.showErrorDialog(e, new DialogText("Form show error", "", "Can't open main form"), rootLogger);
        }
    }

    public void showTableView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/TableView.fxml"));
            AnchorPane tableView = loader.load();

            mainView.setCenter(tableView);

            //TableViewController controller = loader.getController();
            tableViewController = loader.getController();
            tableViewController.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
            Dialogs.showErrorDialog(e, new DialogText("Form show error", "", "Can't open main form"), rootLogger);
        }
    }


    public void setAppTitle(String fileName) {
        if (fileName == null) {
            primaryStage.setTitle(APP_NAME);
        } else {
            primaryStage.setTitle(APP_NAME + ": " + fileName);
        }
    }

    public void showRowInfoView(List<Field> fields, String[] strings) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/RowInfoView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Record info");
            stage.getIcons().add(ImageResources.getAppIcon());

            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.initStyle(StageStyle.UNDECORATED);

            RowInfoViewController recordInfoController = fxmlLoader.getController();
            recordInfoController.setDialogStage(stage);
            recordInfoController.initInfoDialog(fields, strings);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Dialogs.showErrorDialog(e, new DialogText("Form show error", "", "Can't open form"), rootLogger);
        }
    }

    public String[] showTableFilterView(List<Field> dbfFields, String[] filter) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TableFilterView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Filter");
            stage.getIcons().add(ImageResources.getAppIcon());

            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.initStyle(StageStyle.UNDECORATED);

            TableFilterViewController filterController = fxmlLoader.getController();
            filterController.setDialogStage(stage);
            filterController.initInfoDialog(dbfFields, filter);

            stage.showAndWait();
            return filterController.getFilterString();
        } catch (Exception e) {
            e.printStackTrace();
            Dialogs.showErrorDialog(e, new DialogText("Form show error", "", "Can't open form"), rootLogger);
            return null;
        }
    }

    public void showTableInfoView(TableFile tableFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TableInfoView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Table properties");
            stage.getIcons().add(ImageResources.getAppIcon());

            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.initStyle(StageStyle.UNDECORATED);

            TableInfoViewController tableInfoController = fxmlLoader.getController();
            tableInfoController.setDialogStage(stage);
            tableInfoController.initInfoDialog(tableFile);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Dialogs.showErrorDialog(e, new DialogText("Form show error", "", "Can't open form"), rootLogger);
        }
    }

    public boolean tableHasData() {
        if (tableFile == null || tableFile.getFile() == null || tableFile.getTableData() == null
                || tableFile.getTableData().getFields() == null || tableFile.getTableData().getFields().isEmpty()
                || tableFile.getTableData().getData() == null) {
            return false;
        }
        return true;
    }
}
