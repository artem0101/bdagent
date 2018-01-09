package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Apartment;
import objects.Seller;
import utils.DialogManager;

public class SellerApartmentDialogController {

    @FXML
    private TextField tfIdSeller;

    @FXML
    private TextField tfLastNameSeller;

    @FXML
    private TextField tfFirstNameSeller;

    @FXML
    private TextField tfPatronymicSeller;

    @FXML
    private TextField tfBirthdaySeller;

    @FXML
    private TextField tfPhoneSeller;

    @FXML
    private TextField tfIdObjectApartment;

    @FXML
    private TextField tfDistinctApartment;

    @FXML
    private TextField tfAddressApartment;

    @FXML
    private TextField tfPriseApartment;

    @FXML
    private TextField tfFloorApartment;

    @FXML
    private TextField tfRoomsApartment;

    @FXML
    private TextField tfAreaApartment;

    private Seller seller;
    private Apartment apartment;

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setSeller(Seller seller, Apartment apartment) {
        if (seller == null || apartment == null) return;
        this.seller = seller;
        this.apartment = apartment;
        tfIdSeller.setText(seller.getId());
        tfLastNameSeller.setText(seller.getLastName());
        tfFirstNameSeller.setText(seller.getFirstName());
        tfPatronymicSeller.setText(seller.getPatronymic());
        tfBirthdaySeller.setText(seller.getBrthDate());
        tfPhoneSeller.setText(seller.getPhone());
        tfIdObjectApartment.setText(seller.getObjId());
        tfDistinctApartment.setText(apartment.getDistinct());
        tfAddressApartment.setText(apartment.getAddress());
        tfPriseApartment.setText(apartment.getPrice());
        tfFloorApartment.setText(apartment.getFloor());
        tfRoomsApartment.setText(apartment.getRooms());
        tfAreaApartment.setText(apartment.getArea());
    }

    public Seller getSeller() {
        return seller;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void actionSave(ActionEvent actionEvent) {
        if (tfIdSeller.getText().equalsIgnoreCase("") || tfLastNameSeller.getText().equalsIgnoreCase("") ||
                tfFirstNameSeller.getText().equalsIgnoreCase("") || tfPatronymicSeller.getText().equalsIgnoreCase("") ||
                tfBirthdaySeller.getText().equalsIgnoreCase("") || tfPhoneSeller.getText().equalsIgnoreCase("") ||
                tfIdObjectApartment.getText().equalsIgnoreCase("") || tfDistinctApartment.getText().equalsIgnoreCase("") ||
                tfAddressApartment.getText().equalsIgnoreCase("") || tfPriseApartment.getText().equalsIgnoreCase("") ||
                tfFloorApartment.getText().equalsIgnoreCase("") || tfRoomsApartment.getText().equalsIgnoreCase("") ||
                tfAreaApartment.getText().equalsIgnoreCase("")) {
            DialogManager.showInfoDialog("Ошибка", "Введены не все данные");
        } else {
            seller.setId(tfIdSeller.getText());
            seller.setLastName(tfLastNameSeller.getText());
            seller.setFirstName(tfFirstNameSeller.getText());
            seller.setPatronymic(tfPatronymicSeller.getText());
            seller.setBrthDate(tfBirthdaySeller.getText());
            seller.setObjId(tfIdObjectApartment.getText());
            seller.setPhone(tfPhoneSeller.getText());

            apartment.setId(tfIdObjectApartment.getText());
            apartment.setDistinct(tfDistinctApartment.getText());
            apartment.setAddress(tfAddressApartment.getText());
            apartment.setPrice(tfPriseApartment.getText());
            apartment.setFloor(tfFloorApartment.getText());
            apartment.setRooms(tfRoomsApartment.getText());
            apartment.setArea(tfAreaApartment.getText());

            actionClose(actionEvent);
        }
    }
}
