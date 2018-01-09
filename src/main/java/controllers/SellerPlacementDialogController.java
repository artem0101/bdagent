package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Placement;
import objects.Seller;
import utils.DialogManager;

public class SellerPlacementDialogController {

    @FXML
    private TextField tfIdSellerPlacement;

    @FXML
    private TextField tfLastNameSellerPlacement;

    @FXML
    private TextField tfFirstNameSellerPlacement;

    @FXML
    private TextField tfPatronimycSellerPlacement;

    @FXML
    private TextField tfBirthdaySellerPlacement;

    @FXML
    private TextField tfPhoneSellerPlacement;

    @FXML
    private TextField tfIdObjectPlacement;

    @FXML
    private TextField tfDistinctPlacement;

    @FXML
    private TextField tfAddressPlacement;

    @FXML
    private TextField tfPricePlacement;

    @FXML
    private TextField tfFloorsPlacement;

    @FXML
    private TextField tfRoomsPlacement;

    @FXML
    private TextField tfAreaPlacement;

    private Seller seller;

    private Placement placement;

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setSeller(Seller seller, Placement placement) {
        if (seller == null || placement == null) return;
        this.seller = seller;
        this.placement = placement;
        tfIdSellerPlacement.setText(seller.getId());
        tfLastNameSellerPlacement.setText(seller.getLastName());
        tfFirstNameSellerPlacement.setText(seller.getFirstName());
        tfPatronimycSellerPlacement.setText(seller.getPatronymic());
        tfBirthdaySellerPlacement.setText(seller.getBrthDate());
        tfPhoneSellerPlacement.setText(seller.getPhone());
        tfIdObjectPlacement.setText(seller.getObjId());
        tfDistinctPlacement.setText(placement.getDistinct());
        tfAddressPlacement.setText(placement.getAddress());
        tfPricePlacement.setText(placement.getPrice());
        tfFloorsPlacement.setText(placement.getFloors());
        tfRoomsPlacement.setText(placement.getRooms());
        tfAreaPlacement.setText(placement.getArea());
    }

    public void actionSave(ActionEvent actionEvent) {
        if (tfIdSellerPlacement.getText().equalsIgnoreCase("") || tfLastNameSellerPlacement.getText().equalsIgnoreCase("") ||
                tfFirstNameSellerPlacement.getText().equalsIgnoreCase("") || tfPatronimycSellerPlacement.getText().equalsIgnoreCase("") ||
                tfBirthdaySellerPlacement.getText().equalsIgnoreCase("") || tfPhoneSellerPlacement.getText().equalsIgnoreCase("") ||
                tfPhoneSellerPlacement.getText().equalsIgnoreCase("") || tfIdObjectPlacement.getText().equalsIgnoreCase("") ||
                tfDistinctPlacement.getText().equalsIgnoreCase("") || tfAddressPlacement.getText().equalsIgnoreCase("") ||
                tfPricePlacement.getText().equalsIgnoreCase("") || tfFloorsPlacement.getText().equalsIgnoreCase("") ||
                tfRoomsPlacement.getText().equalsIgnoreCase("") || tfAreaPlacement.getText().equalsIgnoreCase("")) {
            DialogManager.showInfoDialog("Ошибка", "Введены не все данные");
        } else {
            seller.setId(tfIdSellerPlacement.getText());
            seller.setLastName(tfLastNameSellerPlacement.getText());
            seller.setFirstName(tfFirstNameSellerPlacement.getText());
            seller.setPatronymic(tfPatronimycSellerPlacement.getText());
            seller.setBrthDate(tfBirthdaySellerPlacement.getText());
            seller.setObjId(tfIdObjectPlacement.getText());
            seller.setPhone(tfPhoneSellerPlacement.getText());

            placement.setId(tfIdObjectPlacement.getText());
            placement.setDistinct(tfDistinctPlacement.getText());
            placement.setAddress(tfAddressPlacement.getText());
            placement.setPrice(tfPricePlacement.getText());
            placement.setFloors(tfFloorsPlacement.getText());
            placement.setRooms(tfRoomsPlacement.getText());
            placement.setArea(tfAreaPlacement.getText());
        }
    }

    public Seller getSeller() {
        return seller;
    }

    public Placement getPlacement() {
        return placement;
    }
}
