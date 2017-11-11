package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Seller;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utils.DialogManager;


public class SellerDialogController {

    @FXML
    private TextField tf_seller;

    @FXML
    private TextField tf_surname;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_patronymic;

    @FXML
    private TextField tf_birthday;

    @FXML
    private TextField tf_idobj_seller;

    @FXML
    private TextField tf_phone_seller;

    private Seller seller;

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setSeller(Seller seller) {
        if (seller == null) return;
        this.seller = seller;
        tf_seller.setText(seller.getId());
        tf_surname.setText(seller.getLastName());
        tf_name.setText(seller.getFirstName());
        tf_patronymic.setText(seller.getPatronymic());
        tf_birthday.setText(seller.getBrthDate());
        tf_idobj_seller.setText(seller.getObjId());
        tf_phone_seller.setText(seller.getPhone());
    }

    public void actionSave(ActionEvent actionEvent) {
        if (tf_seller.getText().equals("") || tf_surname.getText().equals("") || tf_name.getText().equals("") ||
                tf_patronymic.getText().equals("") || tf_birthday.getText().equals("") || tf_idobj_seller.getText().equals("") ||
                tf_phone_seller.getText().equals("")) {
            DialogManager.showInfoDialog("Ошибка", "Введены не все данные");
        } else {
            System.out.println(seller.toString());
            seller.setId(tf_seller.getText());
            seller.setLastName(tf_surname.getText());
            seller.setFirstName(tf_name.getText());
            seller.setPatronymic(tf_patronymic.getText());
            seller.setBrthDate(tf_birthday.getText());
            seller.setObjId(tf_idobj_seller.getText());
            seller.setPhone(tf_phone_seller.getText());
            actionClose(actionEvent);
        }
    }

    public Seller getSeller() {
        return seller;
    }
}
