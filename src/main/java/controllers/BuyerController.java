package controllers;

import interfaces.CollectionBuyer;
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
import objects.Buyer;
import utils.DialogManager;

import java.io.IOException;

public class BuyerController {
    private CollectionBuyer collectionBuyer = new CollectionBuyer();
    private Stage mainStage;
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private BuyerDialogController buyerDialogController;
    private Stage editDialogStage;
    private Stage newEditDialogStage;
    private ObservableList<Buyer> backupList;
    OnCreateStage creating = new OnCreateStage();

    @FXML
    private TableView tableBuyer;

    @FXML
    private TableColumn<Buyer, String> tableIdBuyer;

    @FXML
    private TableColumn<Buyer, String> tableLastNameBuyer;

    @FXML
    private TableColumn<Buyer, String> tableFirstNameBuyer;

    @FXML
    private TableColumn<Buyer, String> tablePatronymicBuyer;

    @FXML
    private TableColumn<Buyer, String> tableBirthdayBuyer;

    @FXML
    private TableColumn<Buyer, String> tablePhoneBuyer;

    @FXML
    private TextField tfSearchBuyer;

    @FXML
    private Label labelCountBuyer;

    @FXML
    private void initialize() {
        tableIdBuyer.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableLastNameBuyer.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        tableFirstNameBuyer.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        tablePatronymicBuyer.setCellValueFactory(new PropertyValueFactory<>("Patronymic"));
        tableBirthdayBuyer.setCellValueFactory(new PropertyValueFactory<>("Birthday"));
        tablePhoneBuyer.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        initListener();
        collectionBuyer.fillTestDataSeller();
        backupList = FXCollections.observableArrayList();
        backupList.addAll(collectionBuyer.getBuyerObservableList());
        tableBuyer.setItems(collectionBuyer.getBuyerObservableList());

//        initListener();
        initLoader();
    }

    private void initListener() {
        collectionBuyer.getBuyerObservableList().addListener((ListChangeListener<Buyer>) c -> updateCountLabel());

        tableBuyer.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                buyerDialogController.setBuyer((Buyer) tableBuyer.getSelectionModel().getSelectedItem());
                showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
            }
        });
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../addBuyer.fxml"));
            fxmlEdit = fxmlLoader.load();
            buyerDialogController = fxmlLoader.getController();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void updateCountLabel() {
        labelCountBuyer.setText("Количество покупателей: " + collectionBuyer.getBuyerObservableList().size());
    }

//    private void setupClearButtonField(CustomTextField customTextField) {
//        try {
//            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
//            m.setAccessible(true);
//            m.invoke(null, customTextField, customTextField.rightProperty());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) return;
        Button clickedButton = (Button) source;
        Buyer selectedBuyer = (Buyer) tableBuyer.getSelectionModel().getSelectedItem();
        buyerDialogController.setBuyer(selectedBuyer);

        switch (clickedButton.getId()) {
            case "btnAddBuyer":
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                buyerDialogController.setBuyer(new Buyer());
                showDialog(editDialogStage);
                if (!buyerDialogController.getBuyer().getId().equals("") ||
                        !buyerDialogController.getBuyer().getLastName().equals("") ||
                        !buyerDialogController.getBuyer().getFirstName().equals("") ||
                        !buyerDialogController.getBuyer().getPatronymic().equals("") ||
                        !buyerDialogController.getBuyer().getBirthday().equals("") ||
                        !buyerDialogController.getBuyer().getPhone().equals("")) {
                    collectionBuyer.add(buyerDialogController.getBuyer());
                    backupList.add(collectionBuyer.lasted());
                }
                System.out.println("add " + selectedBuyer);
                break;
            case "btnEditBuyer":
                if (!(buyerIsSelected(selectedBuyer))) return;
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                showDialog(editDialogStage);
                buyerDialogController.setBuyer(selectedBuyer);
                collectionBuyer.getBuyerObservableList().clear();
                collectionBuyer.getBuyerObservableList().addAll(backupList);
                System.out.println(selectedBuyer);
                break;
            case "btnDelBuyer":
                if (!buyerIsSelected(selectedBuyer)) return;
                collectionBuyer.delete(selectedBuyer);
                break;
            case "btnEmployeesFromBuyer":
                optionsForNewWindow(actionEvent, "..//employees.fxml", "Сотрудники");
                break;
            case "btnSellersFromBuyer":
                optionsForNewWindow(actionEvent, "..//sellers.fxml", "Продавцы");
                break;
        }
    }

    private boolean buyerIsSelected(Buyer selectedBuyer) {
        if (selectedBuyer == null) {
            DialogManager.showInfoDialog("Ошибка", "Выберите запись");
            return false;
        }
        return true;
    }

    public void actionSearch(ActionEvent actionEvent) {
        collectionBuyer.getBuyerObservableList().clear();
        for (Buyer buyer : backupList) {
            if (buyer.getId().toLowerCase().contains(tfSearchBuyer.getText().toLowerCase()) ||
                    buyer.getLastName().toLowerCase().contains(tfSearchBuyer.getText().toLowerCase()) ||
                    buyer.getFirstName().toLowerCase().contains(tfSearchBuyer.getText().toLowerCase()) ||
                    buyer.getPatronymic().contains(tfSearchBuyer.getText().toLowerCase()) ||
                    buyer.getBirthday().contains(tfSearchBuyer.getText().toLowerCase()) ||
                    buyer.getPhone().contains(tfSearchBuyer.getText().toLowerCase()))
                collectionBuyer.getBuyerObservableList().add(buyer);
        }
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
}
