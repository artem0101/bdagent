package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
// подлежит удалению
public class ModalityDialogWindow {
    FXMLLoader fxmlLoader = new FXMLLoader();
    private Stage mainStage, creating_stage, dialogStage;
    private static Parent fxmlStage, parent;
    private int width, height;
    private EmployeeDialogController employeeDialogController;


    public void onCreateStage(Stage stage, String fxmlLocation, String title) {
        mainStage = stage;
//        width = (int) mainStage.getWidth();
//        height = (int) mainStage.getHeight();

        System.out.println(width + "\n" + height);

        try {
            fxmlLoader.setLocation(getClass().getResource(fxmlLocation));
            fxmlStage = fxmlLoader.load();
            employeeDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (creating_stage == null) {
            creating_stage = new Stage();
            creating_stage.setTitle(title);
//            creating_stage.setHeight(height);
//            creating_stage.setWidth(width);
            creating_stage.setResizable(false);
            creating_stage.setScene(new Scene(fxmlStage));
            creating_stage.initModality(Modality.WINDOW_MODAL);
            creating_stage.initOwner(mainStage);
        }
        creating_stage.showAndWait();
    }
}
