package controllers;

import interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.*;
import utils.DialogManager;


public class TransactionDialogController {
    private Transaction transaction;
    private ObservableList<Seller> sellers = FXCollections.observableArrayList();
    private ObservableList<Apartment> apartments = FXCollections.observableArrayList();
    private ObservableList<Ground> grounds = FXCollections.observableArrayList();
    private ObservableList<House> houses = FXCollections.observableArrayList();
    private ObservableList<Placement> placements = FXCollections.observableArrayList();
    private ObservableList<Buyer> buyers = FXCollections.observableArrayList();
    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    private CollectionSeller collectionSeller = new CollectionSeller();
    private CollectionApartment collectionApartment = new CollectionApartment();
    private CollectionGround collectionGround = new CollectionGround();
    private CollectionHouse collectionHouse = new CollectionHouse();
    private CollectionPlacement collectionPlacement = new CollectionPlacement();
    private CollectionBuyer collectionBuyer = new CollectionBuyer();
    private CollectionEmployee collectionEmployee = new CollectionEmployee();

    private static String objId;
    private static String seller;
    private static String buyer;
    private static String employee;
    private static String amount;

    @FXML
    private TextField tfIdTransaction;

    @FXML
    private TextField tfIdObj;

    @FXML
    private TextField tfIdSeller;

    @FXML
    private TextField tfIdBuyer;

    @FXML
    private TextField tfIdEmployee;

    @FXML
    private TextField tfDateTransaction;

    @FXML
    private TextField tfAmountTransaction;

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setTransaction(Transaction transaction) {
        if (transaction == null) return;
        this.transaction = transaction;
        tfIdTransaction.setText(transaction.getId());
        tfIdObj.setText(transaction.getIdObj());
        tfIdSeller.setText(transaction.getSeller());
        tfIdBuyer.setText(transaction.getBuyer());
        tfIdEmployee.setText(transaction.getEmployee());
        tfDateTransaction.setText(transaction.getDate());
        tfAmountTransaction.setText(transaction.getAmount());
    }

    public void actionSave(ActionEvent actionEvent) {
        sellers.addAll(collectionSeller.getSellerList());
        apartments.addAll(collectionApartment.getApartmentObservableList());
        grounds.addAll(collectionGround.getGroundObservableList());
        houses.addAll(collectionHouse.getHouseObservableList());
        placements.addAll(collectionPlacement.getPlacementObservableList());
        buyers.addAll(collectionBuyer.getBuyerObservableList());
        employees.addAll(collectionEmployee.getEmployeeObservableList());

//        sellers.forEach(s -> {
//            if (tfIdObj.getText().equals(s.getObjId()) && tfIdSeller.getText().equals(s.getId())) {
//                employees.forEach(e -> {
//                    if (tfIdEmployee.getText().equals(e.getId())) {
//                        buyers.forEach(b -> {
//                            if (tfIdBuyer.getText().equals(b.getId())) {
//
//                            }
//                        });
//                    }
//                });
//            }
//        });
        if (tfIdTransaction.getText().equalsIgnoreCase("") || tfIdObj.getText().equalsIgnoreCase("") ||
                tfIdSeller.getText().equalsIgnoreCase("") || tfIdBuyer.getText().equalsIgnoreCase("") ||
                tfIdEmployee.getText().equalsIgnoreCase("") || tfDateTransaction.getText().equalsIgnoreCase("") ||
                tfAmountTransaction.getText().equalsIgnoreCase("")) {
            DialogManager.showInfoDialog("Ошибка", "Введены не все данные");
        } else {
            transaction.setId(tfIdTransaction.getText());
            transaction.setIdObj(tfIdObj.getText());
            transaction.setSeller(tfIdSeller.getText());
            transaction.setBuyer(tfIdBuyer.getText());
            transaction.setEmployee(tfIdEmployee.getText());
            transaction.setDate(tfDateTransaction.getText());
            transaction.setAmount(tfAmountTransaction.getText());
            actionClose(actionEvent);
        }
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void afterSelectedObjid(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        TextField textField = (TextField) source;

        switch (textField.getId()) {
            case "tfIdObj":
                sellers.forEach(s -> {
                    if (tfIdObj.getText().equals(s.getObjId())) {
//                        seller = s.getId();
                        tfIdSeller.setText(s.getId());
                        apartments.forEach(a -> {
                            if (tfIdObj.getText().equals(a)) {
//                                amount = a.getPrice();
                                tfAmountTransaction.setText(a.getPrice());
                            }
                        });
                        grounds.forEach(g -> {
                            if (tfIdObj.getText().equals(g.getId())) {
//                                amount = g.getPrice();
                                tfAmountTransaction.setText(g.getPrice());
                            }
                        });
                        houses.forEach(h -> {
                            if (tfIdObj.getText().equals(h.getId())) {
//                                amount = h.getPrice();
                                tfAmountTransaction.setText(h.getPrice());
                            }
                        });
                        placements.forEach(p -> {
                            if (tfIdObj.getText().equals(p.getId())) {
//                                amount = p.getPrice();
                                tfAmountTransaction.setText(p.getPrice());
                            }
                        });
                    } else {
                        DialogManager.showInfoDialog("Ошибка", "Объекта с таким id не существует");
                        tfIdObj.clear();
                    }
                });
                break;
            case "tfIdSeller":
                sellers.forEach(s -> {
                    if (tfIdSeller.getText().equals(s.getId())) {
//                        objId = s.getObjId();
                        tfIdObj.setText(s.getObjId());
                        apartments.forEach(a -> {
                            if (tfIdObj.getText().equals(a)) {
//                                amount = a.getPrice();
                                tfAmountTransaction.setText(a.getPrice());
                            }
                        });
                        grounds.forEach(g -> {
                            if (tfIdObj.getText().equals(g.getId())) {
//                                amount = g.getPrice();
                                tfAmountTransaction.setText(g.getPrice());
                            }
                        });
                        houses.forEach(h -> {
                            if (tfIdObj.getText().equals(h.getId())) {
//                                amount = h.getPrice();
                                tfAmountTransaction.setText(h.getPrice());
                            }
                        });
                        placements.forEach(p -> {
                            if (tfIdObj.getText().equals(p.getId())) {
//                                amount = p.getPrice();
                                tfAmountTransaction.setText(p.getPrice());
                            }
                        });
                    } else {
                        DialogManager.showInfoDialog("Ошибка", "Продавца с таким id не существует");
                        tfIdSeller.clear();
                    }
                });
                break;
            case "tfIdBuyer":
                buyers.forEach(b -> {
                    if (tfIdBuyer.getText().equals(b.getId())) {
                        buyer = tfIdBuyer.getText();
                    } else {
                        DialogManager.showInfoDialog("Ошибка", "Покупателя с таким id не существует");
                        tfIdBuyer.clear();
                    }
                });
                break;
            case "tfIdEmployee":
                employees.forEach(e -> {
                    if (tfIdEmployee.getText().equals(e.getId())) {
                        employee = e.getId();
                    } else {
                        DialogManager.showInfoDialog("Ошибка", "Покупателя с таким id не существует");
                        tfIdEmployee.clear();
                    }
                });
                break;
        }
    }
}
