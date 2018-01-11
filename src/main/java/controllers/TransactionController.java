package controllers;

import interfaces.CollectionTransaction;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Transaction;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utils.DialogManager;

import java.io.IOException;
import java.lang.reflect.Method;

public class TransactionController {
    private CollectionTransaction collectionTransaction = new CollectionTransaction();
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private TransactionDialogController transactionDialogController;
    private Stage editDialogStage;
    private Stage newEditDialogStage;
    private ObservableList<Transaction> backupList;
    private OnCreateStage creating = new OnCreateStage();

    @FXML
    private TableView tableTransaction;

    @FXML
    private TableColumn<Transaction, String> tableId;

    @FXML
    private TableColumn<Transaction, String> tableObj;

    @FXML
    private TableColumn<Transaction, String> tableSeller;

    @FXML
    private TableColumn<Transaction, String> tableBuyer;

    @FXML
    private TableColumn<Transaction, String> tableEmpoloyeer;

    @FXML
    private TableColumn<Transaction, String> tableDate;

    @FXML
    private TableColumn<Transaction, String> tableAmount;

    @FXML
    private Label labelCount;

    @FXML
    private void initialize() {
        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableObj.setCellValueFactory(new PropertyValueFactory<>("Object"));
        tableSeller.setCellValueFactory(new PropertyValueFactory<>("Seller"));
        tableBuyer.setCellValueFactory(new PropertyValueFactory<>("Buyer"));
        tableEmpoloyeer.setCellValueFactory(new PropertyValueFactory<>("Employee"));
        tableDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tableAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));

        collectionTransaction.fillTestDataTransaction();
        backupList = FXCollections.observableArrayList();
        backupList.addAll(collectionTransaction.getTransactionObservableList());
        tableTransaction.setItems(collectionTransaction.getTransactionObservableList());

        initListener();
        initLoader();
    }

    private void initListener() {
        collectionTransaction.getTransactionObservableList().addListener((ListChangeListener) c -> updateCountLabel());

        tableTransaction.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                transactionDialogController.setTransaction((Transaction) tableTransaction.getSelectionModel().getSelectedItem());
                showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
            }
        });
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../addTransaction.fxml"));
            fxmlEdit = fxmlLoader.load();
            transactionDialogController = fxmlLoader.getController();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void updateCountLabel() {
        labelCount.setText("Количество сделок: " + collectionTransaction.getTransactionObservableList().size());
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

    }

    private boolean transactionIsSelected(Transaction selectedTransaction) {
        if (selectedTransaction == null) {
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
}
