package controllers;

import interfaces.CollectionApartment;
import interfaces.CollectionSeller;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Apartment;
import objects.Seller;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utils.DialogManager;

import java.io.IOException;
import java.lang.reflect.Method;

public class SellerApartmentController {
    private CollectionSeller collectionSellerApartment = new CollectionSeller();
    private CollectionApartment collectionApartment = new CollectionApartment();
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private SellerApartmentDialogController sellerApartmentDialogController;
    private Stage editDialogStage;
    private Stage newEditDialogStage;
    private ObservableList<Seller> backupListSellerApartment;
    private ObservableList<Seller> secondBackupSellerListApartment;
    private ObservableList<Apartment> backupListApartment;
    private ObservableList<Apartment> secondBackupListApartment;
    private static Apartment selectedApartment;
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
    private ChoiceBox choiceObj;

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
        tableSeller.setItems(collectionSellerApartment.fillTestDataSellerApartment());

        collectionApartment.fillTestDataApartment();
        backupListApartment = FXCollections.observableArrayList();
        backupListApartment.addAll(collectionApartment.getApartmentObservableList());
        secondBackupListApartment = FXCollections.observableArrayList();

        initListener();
        initLoader();
    }

    private void initListener() {
        try {
            collectionSellerApartment.fillTestDataSellerApartment().addListener((ListChangeListener<Seller>) c -> updateCountLabel());
            tableSeller.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && tableSeller.getSelectionModel().getSelectedItem() != null) {
                    Seller sellerApartment = (Seller) tableSeller.getSelectionModel().getSelectedItem();
                    backupListApartment.forEach(backupApartment -> {
                        if (backupApartment.getId().equalsIgnoreCase(sellerApartment.getObjId()))
                            sellerApartmentDialogController.setSeller(sellerApartment, backupApartment);
                    });
                    showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
                } else if (event.getClickCount() == 1 && tableSeller.getSelectionModel().getSelectedItem() != null) {
                    collectionApartment.getApartmentObservableList().clear();
                    secondBackupListApartment.clear();
                    if (tableApartment.getSelectionModel().getSelectedItems().isEmpty()) {
                        tableApartment.getItems().clear();
                        Seller sellerApartment = (Seller) tableSeller.getSelectionModel().getSelectedItem();
                        backupListApartment.forEach(backupHouse -> {
                            if (sellerApartment.getObjId().equalsIgnoreCase(backupHouse.getId())) {
                                secondBackupListApartment.add(backupHouse);
                                tableApartment.setItems(secondBackupListApartment);
                            } else {
                                System.out.println(sellerApartment.getObjId() + " != " + backupHouse.getId());
                            }
                        });
                    } else {
                        tableApartment.getSelectionModel().getSelectedItems().clear();
                    }
                }
            });
        } catch (NullPointerException exc) {
            System.out.println("is empty");
        }
    }

    private void updateCountLabel() {
        labelCountSellerApartment.setText("Количество покупателей: " + collectionSellerApartment.fillTestDataSellerApartment().size());
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../addSellerApartment.fxml"));
            fxmlEdit = fxmlLoader.load();
            sellerApartmentDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) return;
        Button clickedButton = (Button) source;
        Seller selectedSeller = (Seller) tableSeller.getSelectionModel().getSelectedItem();
        if (selectedSeller != null) {
            backupListApartment.forEach(apartment -> {
                if (apartment.getId().equalsIgnoreCase(selectedSeller.getObjId()))
                    selectedApartment = apartment;
            });

            sellerApartmentDialogController.setSeller(selectedSeller, selectedApartment);

            switch (clickedButton.getId()) {
                case "btnAddSeller":
                    editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    sellerApartmentDialogController.setSeller(new Seller(), new Apartment());
                    showDialog(editDialogStage);
                    if (!sellerApartmentDialogController.getSeller().getId().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getSeller().getLastName().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getSeller().getFirstName().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getSeller().getPatronymic().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getSeller().getBrthDate().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getSeller().getObjId().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getSeller().getPhone().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getApartment().getDistinct().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getApartment().getAddress().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getApartment().getPrice().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getApartment().getFloor().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getApartment().getRooms().equalsIgnoreCase("") ||
                            !sellerApartmentDialogController.getApartment().getArea().equalsIgnoreCase("")) {
                        collectionSellerApartment.add(sellerApartmentDialogController.getSeller());
                        backupListSellerApartment.add(collectionSellerApartment.lasted());

                        collectionApartment.add(sellerApartmentDialogController.getApartment());
                        backupListApartment.add(collectionApartment.lasted());
                    }
                    backupListSellerApartment.forEach(s -> {
                        backupListApartment.forEach(a -> {
                            if (s.getObjId().equals(a.getId()))
                                secondBackupSellerListApartment.add(new Seller(s.getId(), s.getLastName(), s.getFirstName(), s.getPatronymic(), s.getBrthDate(), s.getObjId(), s.getPhone()));
                        });
                    });
                    tableSeller.refresh();
                    tableSeller.setItems(secondBackupSellerListApartment);
                    break;
                case "btnEditSeller":
                    if (!sellerIsSelected(selectedSeller)) return;
                    editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    showDialog(editDialogStage);
                    sellerApartmentDialogController.setSeller(selectedSeller, selectedApartment);
                    collectionSellerApartment.getSellerList().clear();
                    collectionSellerApartment.getSellerList().addAll(backupListSellerApartment);
                    break;
                case "btnDeleteSeller":
                    if (!sellerIsSelected(selectedSeller)) return;
                    collectionSellerApartment.delete(selectedSeller);
                    backupListSellerApartment.clear();
                    backupListSellerApartment.addAll(collectionSellerApartment.getSellerList());
                    collectionApartment.delete(selectedApartment);
                    secondBackupSellerListApartment.clear();
                    backupListSellerApartment.forEach(s -> {
                        backupListApartment.forEach(a -> {
                            if (s.getObjId().equals(a.getId()))
                                secondBackupSellerListApartment.add(new Seller(s.getId(), s.getLastName(), s.getFirstName(), s.getPatronymic(), s.getBrthDate(), s.getObjId(), s.getPhone()));
                        });
                    });
                    tableSeller.setItems(secondBackupSellerListApartment);
                    break;
                case "btn_employees_Seller":
                    optionsForNewWindow(actionEvent, "../employees.fxml", "Сотрудники");
                    break;
                case "btn_buyer_Seller":
                    optionsForNewWindow(actionEvent, "../buyers.fxml", "Покупатели");
                    break;
                case "btn_transaction_Seller":
                    optionsForNewWindow(actionEvent, "../transaction.fxml", "Операции");
                    break;
            }
        }
    }

    private boolean sellerIsSelected(Seller selectedSeller) {
        if (selectedSeller == null) {
            DialogManager.showInfoDialog("Ошибка", "Выберите запись");
            return false;
        }
        return true;
    }

    private void showDialog(Stage stage) {
        if (newEditDialogStage == null) {
            newEditDialogStage = new Stage();
            newEditDialogStage.setResizable(false);
            newEditDialogStage.setScene(new Scene(fxmlEdit));
            newEditDialogStage.initModality(Modality.WINDOW_MODAL);
            newEditDialogStage.initOwner(stage);
        }
        newEditDialogStage.showAndWait();
    }

    private void optionsForNewWindow(ActionEvent actionEvent, String fxmlLocation, String title) {
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setWidth(1300);
        app_stage.setHeight(600);
        creating.onCreateStage(actionEvent, fxmlLocation, title);
        app_stage.close();
    }

    public void choiceCalc(ActionEvent actionEvent) {
        if (choiceObj.getValue().equals("Земельные участки")) {
            optionsForNewWindow(actionEvent, "../sellerGround.fxml", "Продавцы");
        } else if (choiceObj.getValue().equals("Нежелые помещения")) {
            optionsForNewWindow(actionEvent, "../sellerPlacement.fxml", "Продавцы");
        } else if (choiceObj.getValue().equals("Дома")) {
            optionsForNewWindow(actionEvent, "../sellerHouse.fxml", "Продавцы");
        }
    }
}
