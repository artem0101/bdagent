package controllers;

import interfaces.CollectionEmployee;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class OnCreateStage {
//    private static FXMLLoader fxmlLoader = new FXMLLoader();
    private Stage app_stage, creating_stage;
    private static Parent fxmlStage;
    private int width, height;

    public void onCreateStage(ActionEvent actionEvent, String fxmlLocation, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        width = (int) app_stage.getWidth();
        height = (int) app_stage.getHeight();

        try {
//            fxmlLoader.setRoot(null);
            fxmlLoader.setLocation(getClass().getResource(fxmlLocation));

            fxmlStage = fxmlLoader.load();
            System.out.println(fxmlLoader.getRoot().toString());
        } catch (IOException e) {
            System.out.println(fxmlLoader.getRoot().toString() + "\n");
            e.printStackTrace();
        }

        if (creating_stage == null) {
            System.out.println(width + "\n" + height);
            creating_stage = new Stage();
            creating_stage.setTitle(title);
//            creating_stage.setHeight(height);
//            creating_stage.setWidth(width);
            creating_stage.setScene(new Scene(fxmlStage));
        }

        creating_stage.show();

        app_stage.close();
    }

    private void testData() {
        CollectionEmployee collectionEmployee = new CollectionEmployee();
        collectionEmployee.fillTestDataEmployee();
        collectionEmployee.printEmployee();
    }
}
