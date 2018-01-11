package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Ground;
import objects.Seller;
import utils.DialogManager;

public class SellerGroundDialogController {

    @FXML
    private TextField tfIdSellerGround;

    @FXML
    private TextField tfLastNameSellerGround;

    @FXML
    private TextField tfFirstNameSellerGround;

    @FXML
    private TextField tfPatronimycSellerGround;

    @FXML
    private TextField tfBirthdaySellerGround;

    @FXML
    private TextField tfPhoneSellerGround;

    @FXML
    private TextField tfIdObjectGround;

    @FXML
    private TextField tfDistinctGround;

    @FXML
    private TextField tfAddressGround;

    @FXML
    private TextField tfPriceGround;

    @FXML
    private TextField tfAreaGround;

    private Seller seller;
    private Ground ground;

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setSeller(Seller seller, Ground ground) {
        if (seller == null || ground == null) return;
        this.seller = seller;
        this.ground = ground;
        tfIdSellerGround.setText(seller.getId());
        tfLastNameSellerGround.setText(seller.getLastName());
        tfFirstNameSellerGround.setText(seller.getFirstName());
        tfPatronimycSellerGround.setText(seller.getPatronymic());
        tfBirthdaySellerGround.setText(seller.getBrthDate());
        tfPhoneSellerGround.setText(seller.getPhone());
        tfIdObjectGround.setText(seller.getObjId());
        tfDistinctGround.setText(ground.getDistinct());
        tfAddressGround.setText(ground.getAddress());
        tfPriceGround.setText(ground.getPrice());
        tfAreaGround.setText(ground.getArea());
    }

    public void actionSave(ActionEvent actionEvent) {
        if (tfIdSellerGround.getText().equalsIgnoreCase("") || tfLastNameSellerGround.getText().equalsIgnoreCase("") ||
                tfFirstNameSellerGround.getText().equalsIgnoreCase("") || tfPatronimycSellerGround.getText().equalsIgnoreCase("") ||
                tfBirthdaySellerGround.getText().equalsIgnoreCase("") || tfPhoneSellerGround.getText().equalsIgnoreCase("") ||
                tfIdObjectGround.getText().equalsIgnoreCase("") || tfDistinctGround.getText().equalsIgnoreCase("") ||
                tfAddressGround.getText().equalsIgnoreCase("") || tfPriceGround.getText().equalsIgnoreCase("") ||
                tfAreaGround.getText().equalsIgnoreCase("")) {
            DialogManager.showInfoDialog("Ошибка", "Введены не все данные");
        } else {
            seller.setId(tfIdSellerGround.getText());
            seller.setLastName(tfLastNameSellerGround.getText());
            seller.setFirstName(tfFirstNameSellerGround.getText());
            seller.setPatronymic(tfPatronimycSellerGround.getText());
            seller.setBrthDate(tfBirthdaySellerGround.getText());
            seller.setPhone(tfPhoneSellerGround.getText());
            seller.setObjId(tfIdObjectGround.getText());
            ground.setId(tfIdObjectGround.getText());
            ground.setDistinct(tfDistinctGround.getText());
            ground.setAddress(tfAddressGround.getText());
            ground.setPrice(tfPriceGround.getText());
            ground.setArea(tfAreaGround.getText());
            actionClose(actionEvent);
        }
    }

    public Seller getSeller() {
        return seller;
    }

    public Ground getGround() {
        return ground;
    }
}
