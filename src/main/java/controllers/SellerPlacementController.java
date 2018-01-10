package controllers;

import interfaces.CollectionPlacement;
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
import objects.Placement;
import objects.Seller;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utils.DialogManager;

import java.io.IOException;
import java.lang.reflect.Method;

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

        tbIdPlacement.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbDistinctPlacement.setCellValueFactory(new PropertyValueFactory<>("Distinct"));
        tbAddressPlacement.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tbPricePlacement.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tbFloorsPlacement.setCellValueFactory(new PropertyValueFactory<>("Floors"));
        tbRoomsPlacement.setCellValueFactory(new PropertyValueFactory<>("Rooms"));
        tbAreaPlacement.setCellValueFactory(new PropertyValueFactory<>("Area"));

        collectionSellerPlacement.fillTestDataSellerPlacement();
        backupListSellerPlacement = FXCollections.observableArrayList();
        secondBackupListSellerPlacement = FXCollections.observableArrayList();
        backupListSellerPlacement.addAll(collectionSellerPlacement.getSellerList());
        tableSeller.setItems(collectionSellerPlacement.fillTestDataSellerPlacement());

        collectionPlacement.fillTestDataPlacement();
        backupListPlacement = FXCollections.observableArrayList();
        backupListPlacement.addAll(collectionPlacement.getPlacementObservableList());
        secondBackupListPlacement = FXCollections.observableArrayList();

        initListener();
        initLoader();
    }

    private void initListener() {
        try {
            collectionSellerPlacement.fillTestDataSellerPlacement().addListener((ListChangeListener<Seller>) c -> updateCountLabel());

            tableSeller.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Seller sellerPlacement = (Seller) tableSeller.getSelectionModel().getSelectedItem();
                    backupListPlacement.forEach(backupPlacement -> {
                        if (backupPlacement.getId().equalsIgnoreCase(sellerPlacement.getObjId())) {
                            sellerPlacementDialogController.setSeller(sellerPlacement, backupPlacement);
                        }
                    });
                    showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
                } else if (event.getClickCount() == 1) {
                    collectionPlacement.getPlacementObservableList().clear();
                    secondBackupListPlacement.clear();
                    if (ptablePlacement.getSelectionModel().getSelectedItems().isEmpty()) {
                        ptablePlacement.getItems().clear();
                        Seller sellerPlacement = (Seller) tableSeller.getSelectionModel().getSelectedItem();
                        backupListPlacement.forEach(backupPlacement -> {
                            if (sellerPlacement.getObjId().equalsIgnoreCase(backupPlacement.getId())) {
                                secondBackupListPlacement.add(backupPlacement);
                                ptablePlacement.setItems(secondBackupListPlacement);
                            }
                        });
                    } else {
                        ptablePlacement.getSelectionModel().getSelectedItems().clear();
                    }
                }
            });
        } catch (NullPointerException e) {
            System.out.println("Тут пусто");
        }
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../addSellerPlacement.fxml"));
            fxmlEdit = fxmlLoader.load();
            sellerPlacementDialogController = fxmlLoader.getController();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void updateCountLabel() {
        labelCountSeller.setText("Количество покупателей: " + collectionSellerPlacement.fillTestDataSellerPlacement().size());
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
            backupListPlacement.forEach(placement -> {
                if (placement.getId().equalsIgnoreCase(selectedSeller.getObjId())) {
                    selectedPlacement = placement;
                }
            });
        }

        sellerPlacementDialogController.setSeller(selectedSeller, selectedPlacement);

        switch (clickedButton.getId()) {
            case "btnAddSeller":
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                sellerPlacementDialogController.setSeller(new Seller(), new Placement());
                showDialog(editDialogStage);
                if (!sellerPlacementDialogController.getSeller().getId().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getSeller().getLastName().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getSeller().getFirstName().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getSeller().getPatronymic().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getSeller().getBrthDate().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getSeller().getObjId().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getSeller().getPhone().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getPlacement().getDistinct().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getPlacement().getAddress().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getPlacement().getPrice().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getPlacement().getFloors().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getPlacement().getRooms().equalsIgnoreCase("") ||
                        !sellerPlacementDialogController.getPlacement().getArea().equalsIgnoreCase("")) {
                    collectionSellerPlacement.add(sellerPlacementDialogController.getSeller());
                    backupListSellerPlacement.add(collectionSellerPlacement.lasted());

                    collectionPlacement.add(sellerPlacementDialogController.getPlacement());
                    backupListPlacement.add(collectionPlacement.latest());
                }

                backupListSellerPlacement.forEach(s -> {
                    backupListPlacement.forEach(p -> {
                        if (s.getObjId().equalsIgnoreCase(p.getId())) {
                            secondBackupListSellerPlacement.add(new Seller(s.getId(), s.getLastName(), s.getFirstName(), s.getPatronymic(), s.getBrthDate(), s.getObjId(), s.getPhone()));
                        }
                    });
                });
                tableSeller.refresh();
                tableSeller.setItems(secondBackupListSellerPlacement);
                break;
            case "btnEditSeller":
                if (!sellerIsSelected(selectedSeller)) return;
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                showDialog(editDialogStage);
                sellerPlacementDialogController.setSeller(selectedSeller, selectedPlacement);
                collectionSellerPlacement.getSellerList().clear();
                collectionSellerPlacement.getSellerList().addAll(backupListSellerPlacement);
                break;
            case "btnDeleteSeller":
                if (!sellerIsSelected(selectedSeller)) return;
                collectionSellerPlacement.delete(selectedSeller);
                backupListSellerPlacement.clear();
                backupListSellerPlacement.addAll(collectionSellerPlacement.getSellerList());
                collectionPlacement.delete(selectedPlacement);
                secondBackupListSellerPlacement.clear();

                backupListSellerPlacement.forEach(s -> {
                    backupListPlacement.forEach(p -> {
                        if (s.getObjId().equalsIgnoreCase(p.getId()))
                            secondBackupListSellerPlacement.add(new Seller(s.getId(), s.getLastName(), s.getFirstName(), s.getPatronymic(), s.getBrthDate(), s.getObjId(), s.getPhone()));
                    });
                });

                tableSeller.setItems(secondBackupListSellerPlacement);
                break;
            case "btn_employees_Seller":
                optionsForNewWindow(actionEvent, "../employees.fxml", "Сотрудники");
                break;
            case "btn_buyer_Seller":
                optionsForNewWindow(actionEvent, "../buyers.fxml", "Покупатели");
                break;
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
            optionsForNewWindow(actionEvent, "../sellerHouse.fxml", "Продавцы");
        } else if (choiceObj.getValue().equals("Квартиры")) {
            optionsForNewWindow(actionEvent, "../sellerApartment.fxml", "Продавцы");
        }
    }
}
