package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.House;
import objects.Seller;
import utils.DialogManager;

public class SellerHouseDialogController {

    @FXML
    private TextField tfIdSellerHouse;

    @FXML
    private TextField tfLastNameSellerHouse;

    @FXML
    private TextField tfFirstNameSellerHouse;

    @FXML
    private TextField tfPatronimycSellerHouse;

    @FXML
    private TextField tfBirthdaySellerHouse;

    @FXML
    private TextField tfPhoneSellerHouse;

    @FXML
    private TextField tfIdObjectHouse;

    @FXML
    private TextField tfDistinctHouse;

    @FXML
    private TextField tfAddressHouse;

    @FXML
    private TextField tfPriceHouse;

    @FXML
    private TextField tfFloorsHouse;

    @FXML
    private TextField tfRoomsHouse;

    @FXML
    private TextField tfAreaGroundHouse;

    @FXML
    private TextField tfAreaHouse;

    private Seller seller;

    private House house;

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void  setSeller(Seller seller, House house) {
        if (seller == null || house == null) return;
        this.seller = seller;
        this.house = house;
        tfIdSellerHouse.setText(seller.getId());
        tfLastNameSellerHouse.setText(seller.getLastName());
        tfFirstNameSellerHouse.setText(seller.getFirstName());
        tfPatronimycSellerHouse.setText(seller.getPatronymic());
        tfBirthdaySellerHouse.setText(seller.getBrthDate());
        tfPhoneSellerHouse.setText(seller.getPhone());
        tfIdObjectHouse.setText(seller.getObjId());
        tfDistinctHouse.setText(house.getDistinct());
        tfPriceHouse.setText(house.getPrice());
        tfAddressHouse.setText(house.getAddress());
        tfFloorsHouse.setText(house.getFloors());
        tfRoomsHouse.setText(house.getRooms());
        tfAreaGroundHouse.setText(house.getArea_ground());
        tfAreaHouse.setText(house.getArea_house());
    }

    public void actionSave(ActionEvent actionEvent) {
        if (tfIdSellerHouse.getText().equals("") || tfLastNameSellerHouse.getText().equals("") ||
                tfFirstNameSellerHouse.getText().equals("") || tfPatronimycSellerHouse.getText().equals("") ||
                tfBirthdaySellerHouse.getText().equals("") || tfPhoneSellerHouse.getText().equals("") ||
                tfIdObjectHouse.getText().equals("") || tfDistinctHouse.getText().equals("") ||
                tfPriceHouse.getText().equals("") || tfAddressHouse.getText().equals("") ||
                tfFloorsHouse.getText().equals("") || tfRoomsHouse.getText().equals("") ||
                tfAreaGroundHouse.getText().equals("") || tfAreaHouse.getText().equals("")) {
            DialogManager.showInfoDialog("Ошибка", "Введены не все данные");
        } else {
            seller.setId(tfIdSellerHouse.getText());
            seller.setLastName(tfLastNameSellerHouse.getText());
            seller.setFirstName(tfFirstNameSellerHouse.getText());
            seller.setPatronymic(tfPatronimycSellerHouse.getText());
            seller.setBrthDate(tfBirthdaySellerHouse.getText());
            seller.setPhone(tfPhoneSellerHouse.getText());
            seller.setObjId(tfIdObjectHouse.getText());
            house.setId(tfIdObjectHouse.getText());
            house.setDistinct(tfDistinctHouse.getText());
            house.setAddress(tfAddressHouse.getText());
            house.setPrice(tfPriceHouse.getText());
            house.setFloors(tfFloorsHouse.getText());
            house.setRooms(tfRoomsHouse.getText());
            house.setArea_ground(tfAreaGroundHouse.getText());
            house.setArea_house(tfAreaHouse.getText());
            actionClose(actionEvent);
        }
    }

    public Seller getSeller() {
        return seller;
    }

    public House getHouse() {
        return house;
    }
}
