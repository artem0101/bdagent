package controllers;

import interfaces.CollectionApartment;
import interfaces.CollectionSeller;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.Apartment;
import objects.Seller;

public class SellerApartmentController {
    private CollectionSeller collectionSellerApartment = new CollectionSeller();
    private CollectionApartment collectionApartment = new CollectionApartment();
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Stage editDialogStage;
    private Stage newEditDialogStage;
    private ObservableList<Seller> backupListSellerApartment;
    private ObservableList<Seller> secondBackupSellerListApartment;
    private ObservableList<Apartment> backupListApartment;
    private ObservableList<Apartment> secondBackupListApartment;
    private static Apartment selectedApartment;
    private OnCreateStage creating = new OnCreateStage();

    @FXML
    private Button btn_employees_Seller;

    @FXML
    private Button btn_buyer_Seller;

    @FXML
    private TableView tableSeller;

    @FXML
    private TableColumn<Seller, String> tableIdSeller;

    @FXML
    private TableColumn<Seller, String> tableLastNameSeller;

    @FXML
    private TableColumn<Seller, String> tableFirstNameSeller;

    @FXML
    private TableColumn<Seller, String> tablePatronymicSeller;

    @FXML
    private TableColumn<Seller, String> tableBirthdaySeller;

    @FXML
    private TableColumn<Seller, String> tableIdObjectSeller;

    @FXML
    private TableColumn<Seller, String> tablePhoneSeller;

    @FXML
    private TableView tableApartment;

    @FXML
    private TableColumn<Apartment, String> tbIdApartment;

    @FXML
    private TableColumn<Apartment, String> tbDistinctApartment;

    @FXML
    private TableColumn<Apartment, String> tbAddressApartment;

    @FXML
    private TableColumn<Apartment, String> tbPriceApartment;

    @FXML
    private TableColumn<Apartment, String> tbFloorApartment;

    @FXML
    private TableColumn<Apartment, String> tbRoomsApartment;

    @FXML
    private TableColumn<Apartment, String> tbAreaApertment;

    @FXML
    private Label labelCountSellerApartment;

    @FXML
    private void initialize() {
        tableIdSeller.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableLastNameSeller.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        tableFirstNameSeller.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        tablePatronymicSeller.setCellValueFactory(new PropertyValueFactory<>("Patronymic"));
        tableBirthdaySeller.setCellValueFactory(new PropertyValueFactory<>("day"));
        tableIdObjectSeller.setCellValueFactory(new PropertyValueFactory<>("ObjId"));///ObjId
        tablePhoneSeller.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        tbIdApartment.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbDistinctApartment.setCellValueFactory(new PropertyValueFactory<>("Distinct"));
        tbAddressApartment.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tbPriceApartment.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tbFloorApartment.setCellValueFactory(new PropertyValueFactory<>("Floor"));
        tbRoomsApartment.setCellValueFactory(new PropertyValueFactory<>("Rooms"));
        tbAreaApertment.setCellValueFactory(new PropertyValueFactory<>("Area"));

        collectionSellerApartment.fillTestDataSellerApartment();
        backupListSellerApartment = FXCollections.observableArrayList();
        secondBackupSellerListApartment = FXCollections.observableArrayList();
        backupListSellerApartment.addAll(collectionSellerApartment.getSellerList());
        tableSeller.setItems(collectionSellerApartment.fillTestDataSellerHouse());

        collectionApartment.fillTestDataApartment();
        backupListApartment = FXCollections.observableArrayList();
        backupListApartment.addAll(collectionApartment.getApartmentObservableList());
        secondBackupListApartment = FXCollections.observableArrayList();
    }

    private void initListener() {
        try {
            collectionSellerApartment.fillTestDataSellerApartment().addListener((ListChangeListener<Seller>) c -> updateCountLabel());
        } catch (NullPointerException exc) {

        }
    }

    private void updateCountLabel() {
        labelCountSellerApartment.setText("Количество покупателей: " + collectionSellerApartment.fillTestDataSellerApartment().size());
    }
}
