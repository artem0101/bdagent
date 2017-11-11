package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectionController {
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtPassword;
    @FXML
    private Label labResult;

    OnCreateStage creating = new OnCreateStage();

    public void onConnection(ActionEvent actionEvent) {
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        if (txtLogin.getText().length() != 0 && txtPassword.getText().length() != 0) {
            if (txtLogin.getText().equals("1") && txtPassword.getText().equals("1")) {
                labResult.setText("Правильно!");
                app_stage.hide();
                app_stage.setWidth(1300);
                app_stage.setHeight(600);
                creating.onCreateStage(actionEvent, "../employees.fxml", "Сотрудники");
                app_stage.close();
            } else {
                labResult.setText("Не правильно(");
            }
        } else {
            labResult.setText("Не были введены данные");
        }
    }
}
