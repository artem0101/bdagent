package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Transaction;
import utils.DialogManager;


public class TransactionDialogController {
    private Transaction transaction;

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
}
