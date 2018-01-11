package controllers;

import interfaces.CollectionGround;
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
import objects.Ground;
import objects.Seller;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utils.DialogManager;

import java.io.IOException;
import java.lang.reflect.Method;

public class SellerGroundController {
    private CollectionSeller collectionSellerGround = new CollectionSeller();
    private CollectionGround collectionGround = new CollectionGround();
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private SellerGroundDialogController sellerGroundDialogController;
    private Stage editDialogStage;
    private Stage newEditDialogStage;
    private ObservableList<Seller> backupListSellerGround;
    private ObservableList<Seller> secondBackupListSellerGround;
    private ObservableList<Ground> backupListGround;
    private ObservableList<Ground> secondBackupListGround;
    private static Ground selectedGround;
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
    private TableView tableGround;

    @FXML
    private TableColumn<Ground, String> tbIdGround;

    @FXML
    private TableColumn<Ground, String> tbDistinctGround;

    @FXML
    private TableColumn<Ground, String> tbAddressGround;

    @FXML
    private TableColumn<Ground, String> tbPriceGround;

    @FXML
    private TableColumn<Ground, String> tbAreaGround;

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

        tbIdGround.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbDistinctGround.setCellValueFactory(new PropertyValueFactory<>("Distinct"));
        tbAddressGround.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tbPriceGround.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tbAreaGround.setCellValueFactory(new PropertyValueFactory<>("Area"));

        collectionSellerGround.fillTestDataSellerGround();
        backupListSellerGround = FXCollections.observableArrayList();
        secondBackupListSellerGround = FXCollections.observableArrayList();
        backupListSellerGround.addAll(collectionSellerGround.getSellerList());
        tableSeller.setItems(collectionSellerGround.fillTestDataSellerGround());

        collectionGround.fillTestDataGround();
        backupListGround = FXCollections.observableArrayList();
        secondBackupListGround = FXCollections.observableArrayList();
        backupListGround.addAll(collectionGround.getGroundObservableList());

        initListener();
        initLoader();
    }

    private void initListener() {
        try {
            collectionSellerGround.fillTestDataSellerGround().addListener((ListChangeListener) c -> updateCountLabel());

            tableSeller.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && tableSeller.getSelectionModel().getSelectedItem() != null) {
                    Seller selectedSeller = (Seller) tableSeller.getSelectionModel().getSelectedItem();
                    backupListGround.forEach(backupGround -> {
                        if (backupGround.getId().equalsIgnoreCase(selectedSeller.getObjId())) {
                            sellerGroundDialogController.setSeller(selectedSeller, backupGround);
                        }
                    });
                    showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
                } else if (event.getClickCount() == 1 && tableSeller.getSelectionModel().getSelectedItem() != null) {
                    collectionGround.getGroundObservableList().clear();
                    secondBackupListGround.clear();
                    if (tableGround.getSelectionModel().getSelectedItems().isEmpty()) {
                        tableGround.getItems().clear();
                        Seller selectedSeller = (Seller) tableSeller.getSelectionModel().getSelectedItem();
                        backupListGround.forEach(backupGround -> {
                            if (selectedSeller.getObjId().equalsIgnoreCase(backupGround.getId())) {
                                secondBackupListGround.add(backupGround);
                                tableGround.setItems(secondBackupListGround);
                            }
                        });
                    } else {
                        tableGround.getSelectionModel().getSelectedItems().clear();
                    }
                }
            });
        } catch (NullPointerException e) {
            System.out.println("Is empty");
        }
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../addSellerGround.fxml"));
            fxmlEdit = fxmlLoader.load();
            sellerGroundDialogController = fxmlLoader.getController();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void updateCountLabel() {
        labelCountSeller.setText("Количество покупателей: " + collectionSellerGround.fillTestDataSellerGround().size());
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
            backupListGround.forEach(ground -> {
                if (ground.getId().equalsIgnoreCase(selectedSeller.getObjId()))
                    selectedGround = ground;
            });
        }

        sellerGroundDialogController.setSeller(selectedSeller, selectedGround);

        switch (clickedButton.getId()) {
            case "btnAddSeller":
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                sellerGroundDialogController.setSeller(new Seller(), new Ground());
                showDialog(editDialogStage);
                if (!sellerGroundDialogController.getSeller().getId().equalsIgnoreCase("") ||
                        !sellerGroundDialogController.getSeller().getLastName().equalsIgnoreCase("") ||
                        !sellerGroundDialogController.getSeller().getFirstName().equalsIgnoreCase("") ||
                        !sellerGroundDialogController.getSeller().getPatronymic().equalsIgnoreCase("") ||
                        !sellerGroundDialogController.getSeller().getBrthDate().equalsIgnoreCase("") ||
                        !sellerGroundDialogController.getSeller().getPhone().equalsIgnoreCase("") ||
                        !sellerGroundDialogController.getSeller().getObjId().equalsIgnoreCase("") ||
                        !sellerGroundDialogController.getGround().getDistinct().equalsIgnoreCase("") ||
                        !sellerGroundDialogController.getGround().getAddress().equalsIgnoreCase("") ||
                        !sellerGroundDialogController.getGround().getPrice().equalsIgnoreCase("") ||
                        !sellerGroundDialogController.getGround().getArea().equalsIgnoreCase("")) {
                    collectionSellerGround.add(sellerGroundDialogController.getSeller());
                    backupListSellerGround.add(collectionSellerGround.lasted());

                    collectionGround.add(sellerGroundDialogController.getGround());
                    backupListGround.add(collectionGround.latest());
                }

                backupListSellerGround.forEach(s -> {
                    backupListGround.forEach(g -> {
                        if (s.getObjId().equalsIgnoreCase(g.getId())) {
                            secondBackupListSellerGround.add(new Seller(s.getId(), s.getLastName(), s.getFirstName(), s.getPatronymic(), s.getBrthDate(), s.getObjId(), s.getPhone()));
                        }
                    });
                });

                tableSeller.refresh();
                tableSeller.setItems(secondBackupListSellerGround);
                break;
            case "btnEditSeller":
                if (!sellerIsSelected(selectedSeller)) return;
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                showDialog(editDialogStage);
                sellerGroundDialogController.setSeller(selectedSeller, selectedGround);
                collectionSellerGround.getSellerList().clear();
                collectionSellerGround.getSellerList().addAll(secondBackupListSellerGround);
                break;
            case "btnDeleteSeller":
                if (!sellerIsSelected(selectedSeller)) return;
                collectionSellerGround.delete(selectedSeller);
                backupListSellerGround.clear();
                backupListSellerGround.addAll(collectionSellerGround.getSellerList());
                collectionGround.delete(selectedGround);
                secondBackupListSellerGround.clear();

                backupListSellerGround.forEach(s -> {
                    backupListGround.forEach(g -> {
                        if (s.getObjId().equalsIgnoreCase(g.getId()))
                            secondBackupListSellerGround.add(new Seller(s.getId(), s.getLastName(), s.getFirstName(), s.getPatronymic(), s.getBrthDate(), s.getObjId(), s.getPhone()));
                    });
                });

                tableSeller.setItems(secondBackupListSellerGround);
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
        if (choiceObj.getValue().equals("Дома")) {
            optionsForNewWindow(actionEvent, "../sellerHouse.fxml", "Продавцы");
        } else if (choiceObj.getValue().equals("Нежелые помещения")) {
            optionsForNewWindow(actionEvent, "../sellerPlacement.fxml", "Продавцы");
        } else if (choiceObj.getValue().equals("Квартиры")) {
            optionsForNewWindow(actionEvent, "../sellerApartment.fxml", "Продавцы");
        }
    }
}
