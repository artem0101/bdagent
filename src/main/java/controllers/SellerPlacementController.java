package controllers;

import interfaces.CollectionPlacement;
import interfaces.CollectionSeller;
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
import objects.Placement;
import objects.Seller;

public class SellerPlacementController {
    private CollectionSeller collectionSellerPlacement = new CollectionSeller();
    private CollectionPlacement collectionPlacement = new CollectionPlacement();
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private SellerPlacementDialogController sellerPlacementDialogController;
    private Stage editDialogStage;
    private Stage newEditDialogStage;
    private ObservableList<Seller> backupListSellerPlacement;
    private ObservableList<Seller> secondBackupListSellerPlacement;
    private ObservableList<Placement> backupListPlacement;
    private ObservableList<Placement> secondBackupListPlacement;
    private static Placement selectedPlacement;
    private OnCreateStage creating = new OnCreateStage();

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
    private TableView ptablePlacement;

    @FXML
    private TableColumn<Placement, String> tbIdPlacement;

    @FXML
    private TableColumn<Placement, String> tbDistinctPlacement;

    @FXML
    private TableColumn<Placement, String> tbAddressPlacement;

    @FXML
    private TableColumn<Placement, String> tbPricePlacement;

    @FXML
    private TableColumn<Placement, String> tbFloorsPlacement;

    @FXML
    private TableColumn<Placement, String> tbRoomsPlacement;

    @FXML
    private TableColumn<Placement, String> tbAreaPlacement;

    @FXML
    private Label labelCountSeller;

    @FXML
    private void initialize() {
        tableIdSeller.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableLastNameSeller.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        tableFirstNameSeller.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        tablePatronymicSeller.setCellValueFactory(new PropertyValueFactory<>("Patronymic"));
        tableBirthdaySeller.setCellValueFactory(new PropertyValueFactory<>("day"));
        tableIdObjectSeller.setCellValueFactory(new PropertyValueFactory<>("ObjId"));///ObjId
        tablePhoneSeller.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        tbIdPlacement.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbDistinctPlacement.setCellValueFactory(new PropertyValueFactory<>("Distinct"));
        tbAddressPlacement.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tbPricePlacement.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tbFloorsPlacement.setCellValueFactory(new PropertyValueFactory<>("Floors"));
        tbRoomsPlacement.setCellValueFactory(new PropertyValueFactory<>("Rooms"));
        tbAreaPlacement.setCellValueFactory(new PropertyValueFactory<>("Area"));

        collectionSellerPlacement.fillTestDataSellerPlacement();
    }
}
