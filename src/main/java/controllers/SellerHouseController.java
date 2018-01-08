package controllers;

import interfaces.CollectionHouse;
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
import objects.House;
import objects.Seller;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utils.DialogManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.AbstractList;

public class SellerHouseController {
    private CollectionSeller collectionSellerHouse = new CollectionSeller();
//    private CollectionSeller collectionSeller = new CollectionSeller();
    private CollectionHouse collectionHouse = new CollectionHouse();
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private SellerHouseDialogController sellerHouseDialogController;
    private Stage editDialogStage;
    private Stage newEditDialogStage;
//    private ObservableList<Seller> backupListSeller;
    private ObservableList<Seller> backupListSellerHouse;
    private ObservableList<Seller> secondBackupListSellerHouse;
    private ObservableList<House> backupListHouse;
    private ObservableList<House> secondBackupListHouse;
    private static House selectedHouseSeller;
    private OnCreateStage creating = new OnCreateStage();
    private static Seller variable;
    private static int i;

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
    private TableView tableHouse;

    @FXML
    private TableColumn<House, String> tbIdHouse;

    @FXML
    private TableColumn<House, String> tbDistinctHouse;

    @FXML
    private TableColumn<House, String> tbAddresshouse;

    @FXML
    private TableColumn<House, String> tbPriceHouse;

    @FXML
    private TableColumn<House, String> tbFloorsHouse;

    @FXML
    private TableColumn<House, String> tbRoomsHouse;

    @FXML
    private TableColumn<House, String> tbGroundHouse;

    @FXML
    private TableColumn<House, String> tbAreaHouse;

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

        tbIdHouse.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbDistinctHouse.setCellValueFactory(new PropertyValueFactory<>("Distinct"));
        tbAddresshouse.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tbPriceHouse.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tbFloorsHouse.setCellValueFactory(new PropertyValueFactory<>("Floors"));
        tbRoomsHouse.setCellValueFactory(new PropertyValueFactory<>("Rooms"));
        tbGroundHouse.setCellValueFactory(new PropertyValueFactory<>("ground"));
        tbAreaHouse.setCellValueFactory(new PropertyValueFactory<>("house"));

//        collectionSeller.fillTestDataSeller();
//        backupListSeller = FXCollections.observableArrayList();
//        backupListSeller.addAll(collectionSeller.getSellerList());
        System.out.println("collectionHouse");

        collectionSellerHouse.fillTestDataSellerHouse();
        backupListSellerHouse = FXCollections.observableArrayList();
        secondBackupListSellerHouse = FXCollections.observableArrayList();
        backupListSellerHouse.addAll(collectionSellerHouse.getSellerList());
        System.out.println("setItems");
        tableSeller.setItems(collectionSellerHouse.fillTestDataSellerHouse()); // TODO  изменить источник

        collectionHouse.fillTestDataHouse();
        backupListHouse = FXCollections.observableArrayList();
        backupListHouse.addAll(collectionHouse.getHouseObservableList());
        secondBackupListHouse = FXCollections.observableArrayList();

        initListener();
        initLoader();
//        setupClearButtonField(tfSearchSeller);
    }

    private void initListener() {
        System.out.println("initListener");
        try {
            collectionSellerHouse.fillTestDataSellerHouse().addListener((ListChangeListener<Seller>) c -> updateCountLabel());
//        backupListHouse.addAll(collectionHouse.getHouseObservableList());
            tableSeller.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Seller sellerHouse = (Seller) tableSeller.getSelectionModel().getSelectedItem();
                    backupListHouse.forEach(backupHouse -> {
                        if (backupHouse.getId().equalsIgnoreCase(sellerHouse.getObjId()))
                            sellerHouseDialogController.setSeller(sellerHouse, backupHouse);
                    });
                    showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
                } else if (event.getClickCount() == 1) {
                    collectionHouse.getHouseObservableList().clear();
                    secondBackupListHouse.clear();
                    if (tableHouse.getSelectionModel().getSelectedItems().isEmpty()) {
                        tableHouse.getItems().clear();
                        Seller sellerHouse = (Seller) tableSeller.getSelectionModel().getSelectedItem();
                        backupListHouse.forEach(backupHouse -> {
                            if (sellerHouse.getObjId().equalsIgnoreCase(backupHouse.getId())) {
                                secondBackupListHouse.add(backupHouse);
                                tableHouse.setItems(secondBackupListHouse);
                            }
                        });
                    } else {
                        tableHouse.getSelectionModel().getSelectedItems().clear();
                    }
                }
            });
        } catch (NullPointerException exc) {
            System.out.println("Тут пусто");
        }

    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../addSellerHouse.fxml"));
            fxmlEdit = fxmlLoader.load();
            sellerHouseDialogController = fxmlLoader.getController();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void updateCountLabel() {
        labelCountSeller.setText("Количество покупателей: " + collectionSellerHouse.fillTestDataSellerHouse().size());
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
            backupListHouse.forEach(house -> {
                if (house.getId().equalsIgnoreCase(selectedSeller.getObjId()))
                    selectedHouseSeller = house;
            });
        }

        sellerHouseDialogController.setSeller(selectedSeller, selectedHouseSeller);

        switch (clickedButton.getId()) {
            case "btnAddSeller":
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                sellerHouseDialogController.setSeller(new Seller(), new House());
                showDialog(editDialogStage);
                if (!sellerHouseDialogController.getSeller().getId().equals("") ||
                        !sellerHouseDialogController.getSeller().getLastName().equals("") ||
                        !sellerHouseDialogController.getSeller().getFirstName().equals("") ||
                        !sellerHouseDialogController.getSeller().getPatronymic().equals("") ||
                        !sellerHouseDialogController.getSeller().getBrthDate().equals("") ||
                        !sellerHouseDialogController.getSeller().getObjId().equals("") ||
                        !sellerHouseDialogController.getSeller().getPhone().equals("") ||
                        !sellerHouseDialogController.getHouse().getDistinct().equals("") ||
                        !sellerHouseDialogController.getHouse().getAddress().equals("") ||
                        !sellerHouseDialogController.getHouse().getPrice().equals("") ||
                        !sellerHouseDialogController.getHouse().getFloors().equals("") ||
                        !sellerHouseDialogController.getHouse().getRooms().equals("") ||
                        !sellerHouseDialogController.getHouse().getArea_ground().equals("") ||
                        !sellerHouseDialogController.getHouse().getArea_house().equals("")) {
                    collectionSellerHouse.add(sellerHouseDialogController.getSeller());
                    backupListSellerHouse.add(collectionSellerHouse.lasted());

                    collectionHouse.add(sellerHouseDialogController.getHouse());
                    backupListHouse.add(collectionHouse.latest());
                }
//                System.out.println("\nSeller");
//                backupListSellerHouse.forEach(s -> System.out.println(s.toString()));
                backupListSellerHouse.forEach(s -> {
                    backupListHouse.forEach(h -> {
                        if (s.getObjId().equals(h.getId()))
                            secondBackupListSellerHouse.add(new Seller(s.getId(), s.getLastName(), s.getFirstName(), s.getPatronymic(), s.getBrthDate(), s.getObjId(), s.getPhone()));
                    });
                });
//                System.out.println("\nHouse");
//                backupListHouse.forEach(h -> System.out.println(h.toString()));
//                System.out.println("****\n");
                tableSeller.refresh();
                tableSeller.setItems(secondBackupListSellerHouse);
                break;
            case "btnEditSeller":
                if (!sellerIsSelected(selectedSeller)) return;
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                showDialog(editDialogStage);
                sellerHouseDialogController.setSeller(selectedSeller, selectedHouseSeller);
                collectionSellerHouse.getSellerList().clear();
                collectionSellerHouse.getSellerList().addAll(backupListSellerHouse);
                System.out.println(selectedSeller);
                break;
            case "btnDeleteSeller":
//                System.out.println("DELETE");
                if (!sellerIsSelected(selectedSeller)) return;

//                System.out.println(selectedSeller.toString());
//                System.out.println("DELETE 2");
//                collectionSellerHouse.delete(selectedSeller);
//                collectionSellerHouse.getSellerList().remove(selectedSeller);
//                CollectionSeller backupCollectionSeller = new CollectionSeller();
//                collectionSellerHouse.getSellerList().forEach(e -> {
//                    Seller selectedSeller3;
//                    if (e.getId().equalsIgnoreCase(selectedSeller.getId()) && e.getLastName().equalsIgnoreCase(selectedSeller.getLastName()) &&
//                    e.getFirstName().equalsIgnoreCase(selectedSeller.getFirstName()) && e.getPatronymic().equalsIgnoreCase(selectedSeller.getPatronymic())) {
//                        selectedSeller3 = new Seller(e.getId(), e.getLastName(), e.getFirstName(), e.getPatronymic(), e.getBrthDate(),e.getObjId(), e.getPhone());
//                        System.out.println(e.toString());
//                        i = indexOf(e);
//                        collectionSellerHouse.delete(e);
//                    }
//                });



//                System.out.println("\n----------------------\n");
//                backupListSellerHouse.clear();
//                System.out.println(backupListSellerHouse.size());
//                backupListSellerHouse.addAll(collectionSellerHouse.getSellerList());
//                backupListSellerHouse.removeAll(selectedSeller);
//                collectionHouse.delete(selectedHouseSeller);
////                backupListSellerHouse.forEach(s -> {
////                    if (backupListSellerHouse.contains(selectedSeller.get)) {
////                        backupListSellerHouse.removeAll(s);
////                        System.out.println("YY");
////                    }
////                });
//                System.out.println("ba");
//                backupListSellerHouse.forEach(s -> System.out.println(s.toString()));
//                secondBackupListSellerHouse.clear();
//                backupListSellerHouse.forEach(s -> {
//                    backupListHouse.forEach(h -> {
//                        if (s.getObjId().equals(h.getId()))
//                            secondBackupListSellerHouse.add(new Seller(s.getId(), s.getLastName(), s.getFirstName(), s.getPatronymic(), s.getBrthDate(), s.getObjId(), s.getPhone()));
//                    });
//                });
//                System.out.println("second");
//                secondBackupListSellerHouse.forEach(s -> System.out.println(s.toString()));
////                System.out.println("\nHouse");
////                backupListHouse.forEach(h -> System.out.println(h.toString()));
////                System.out.println("****\n");
//                tableSeller.setItems(secondBackupListSellerHouse);
//                System.out.println("collectionSellerHouse");
//                collectionSellerHouse.getSellerList().forEach(e -> System.out.println(e.toString()));
//                System.out.println("backupListSellerHouse");
//                backupListSellerHouse.forEach(e -> System.out.println(e.toString()));
//                System.out.println("secondBackupListSellerHouse");
//                secondBackupListSellerHouse.forEach(e -> System.out.println(e.toString()));
                collectionSellerHouse.delete(selectedSeller);
                backupListSellerHouse.clear();
                backupListSellerHouse.addAll(collectionSellerHouse.getSellerList());
                collectionHouse.delete(selectedHouseSeller);
                secondBackupListSellerHouse.clear();
//                System.out.println("__++____");
//                backupListSellerHouse.forEach(e -> System.out.println(e.toString()));
                backupListSellerHouse.forEach(s -> {
                    backupListHouse.forEach(h -> {
                        if (s.getObjId().equals(h.getId()))
                            secondBackupListSellerHouse.add(new Seller(s.getId(), s.getLastName(), s.getFirstName(), s.getPatronymic(), s.getBrthDate(), s.getObjId(), s.getPhone()));
                    });
                });
//                System.out.println("second");
//                secondBackupListSellerHouse.forEach(s -> System.out.println(s.toString()));
//                System.out.println("\nHouse");
//                backupListHouse.forEach(h -> System.out.println(h.toString()));
//                System.out.println("****\n");
                tableSeller.setItems(secondBackupListSellerHouse);
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
        if (choiceObj.getValue().equals("Земельные участки")) {
            optionsForNewWindow(actionEvent, "../sellerGround.fxml", "Продавцы");
        } else if (choiceObj.getValue().equals("Нежелые помещения")) {
            optionsForNewWindow(actionEvent, "../sellerPlacement.fxml", "Продавцы");
        } else if (choiceObj.getValue().equals("Квартиры")) {
            optionsForNewWindow(actionEvent, "../sellerApartment.fxml", "Продавцы");
        }
    }
}