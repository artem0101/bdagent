package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Buyer;
import utils.DialogManager;

public class BuyerDialogController {
    @FXML
    private TextField tfId;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfPatronymic;

    @FXML
    private TextField tfBirthday;

    @FXML
    private TextField tfPhone;

    private Buyer buyer;

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        if (buyer == null) return;
        this.buyer = buyer;
        tfId.setText(buyer.getId());
        tfLastName.setText(buyer.getLastName());
        tfFirstName.setText(buyer.getFirstName());
        tfPatronymic.setText(buyer.getPatronymic());
        tfBirthday.setText(buyer.getBirthday());
        tfPhone.setText(buyer.getPhone());
    }

    public void actionSave(ActionEvent actionEvent) {
        if (tfId.getText().equals("") || tfLastName.getText().equals("") || tfFirstName.getText().equals("") ||
                tfPatronymic.getText().equals("") || tfPhone.getText().equals("")) {
            DialogManager.showInfoDialog("Ошибка", "Введены не все данные");
        } else {
            System.out.println(buyer.toString());
            buyer.setId(tfId.getText());
            buyer.setLastName(tfLastName.getText());
            buyer.setFirstName(tfFirstName.getText());
            buyer.setPatronymic(tfPatronymic.getText());
            buyer.setBirthday(tfBirthday.getText());
            buyer.setPhone(tfPhone.getText());
            actionClose(actionEvent);
        }
    }
}
