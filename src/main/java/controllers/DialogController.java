package controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import objects.Seller;

abstract class DialogController {
    private Seller seller;

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public abstract void setSeller();

    public abstract void actionSave(ActionEvent actionEvent);

    public Seller getSeller() {
        return seller;
    }
}
