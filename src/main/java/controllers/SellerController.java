package controllers;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Seller;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utils.DialogManager;

import java.io.IOException;
import java.lang.reflect.Method;


public class SellerController {
    private CollectionSeller collectionSeller = new CollectionSeller();
    private Stage mainStage;
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private SellerDialogController sellerDialogController;
    private Stage editDialogStage;
    private Stage newEditDialogStage;
    private ObservableList<Seller> backupList;
    static AnchorPane an;
    OnCreateStage creating = new OnCreateStage();



//    @FXML
//    private CustomTextField tfSearchSeller;
//    private TextField tfSearchSeller;

    @FXML
    private Button btn_employees_Seller;

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
    private Label labelCountSeller;

    @FXML
    private ChoiceBox choiceObj;




    @FXML
    private void initialize() {
//        tableSeller.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tableIdSeller.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableLastNameSeller.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        tableFirstNameSeller.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        tablePatronymicSeller.setCellValueFactory(new PropertyValueFactory<>("Patronymic"));
//        tableBirthdaySeller.setCellValueFactory(new PropertyValueFactory<>("Birthday"));  //BirthDate
        tableBirthdaySeller.setCellValueFactory(new PropertyValueFactory<>("day"));
        tableIdObjectSeller.setCellValueFactory(new PropertyValueFactory<>("ObjId"));///ObjId
        tablePhoneSeller.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        collectionSeller.fillTestDataSeller();
        backupList = FXCollections.observableArrayList();
        backupList.addAll(collectionSeller.getSellerList());
        tableSeller.setItems(collectionSeller.getSellerList());

        initListener();
        initLoader();
//        setupClearButtonField(tfSearchSeller);
    }

    private void initListener() {
        collectionSeller.getSellerList().addListener((ListChangeListener<Seller>) c -> updateCountLabel());

        tableSeller.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                sellerDialogController.setSeller((Seller) tableSeller.getSelectionModel().getSelectedItem());
                showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
            }
        });
    }


    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../addSeller.fxml"));
            fxmlEdit = fxmlLoader.load();
            sellerDialogController = fxmlLoader.getController();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void updateCountLabel() {
        labelCountSeller.setText("Количество продавцов: " + collectionSeller.getSellerList().size());
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
        sellerDialogController.setSeller(selectedSeller);

        switch (clickedButton.getId()) {
            case "btnAddSeller":
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                sellerDialogController.setSeller(new Seller());
                showDialog(editDialogStage);
                if (!sellerDialogController.getSeller().getId().equals("") ||
                        !sellerDialogController.getSeller().getLastName().equals("") ||
                        !sellerDialogController.getSeller().getFirstName().equals("") ||
                        !sellerDialogController.getSeller().getPatronymic().equals("") ||
                        !sellerDialogController.getSeller().getBrthDate().equals("") ||
                        !sellerDialogController.getSeller().getObjId().equals("") ||
                        !sellerDialogController.getSeller().getPhone().equals("")) {
                    collectionSeller.add(sellerDialogController.getSeller());
                    backupList.add(collectionSeller.lasted());
                }
                System.out.println("add " + selectedSeller);
                break;
            case "btnEditSeller":
                if (!sellerIsSelected(selectedSeller)) return;
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                showDialog(editDialogStage);
                sellerDialogController.setSeller(selectedSeller);
                collectionSeller.getSellerList().clear();
                collectionSeller.getSellerList().addAll(backupList);
                System.out.println(selectedSeller);
                break;
            case "btnDeleteSeller":
                if (!sellerIsSelected(selectedSeller)) return;
                collectionSeller.delete(selectedSeller);
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

//    public void actionSearch(ActionEvent actionEvent) {
//        collectionSeller.getSellerList().clear();
//        for (Seller seller : backupList) {
//            if (seller.getId().toLowerCase().contains(tfSearchSeller.getText().toLowerCase()) ||
//                    seller.getLastName().toLowerCase().contains(tfSearchSeller.getText().toLowerCase()) ||
//                    seller.getFirstName().toLowerCase().contains(tfSearchSeller.getText().toLowerCase()) ||
//                    seller.getPatronymic().toLowerCase().contains(tfSearchSeller.getText().toLowerCase()) ||
//                    seller.getBrthDate().toLowerCase().contains(tfSearchSeller.getText().toLowerCase()) ||
//                    seller.getObjId().toLowerCase().contains(tfSearchSeller.getText().toLowerCase()) ||
//                    seller.getPhone().toLowerCase().contains(tfSearchSeller.getText().toLowerCase()))
//                collectionSeller.getSellerList().add(seller);
//        }
//    }

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
        } else if (choiceObj.getValue().equals("Земельные участки")) {
            optionsForNewWindow(actionEvent, "../sellerGround.fxml", "Продавцы");
        } else if (choiceObj.getValue().equals("Нежелые помещения")) {
            optionsForNewWindow(actionEvent, "../sellerPlacement.fxml", "Продавцы");
        } else if (choiceObj.getValue().equals("Квартиры")) {
            optionsForNewWindow(actionEvent, "../sellerApartment.fxml", "Продавцы");
        }
    }
}
