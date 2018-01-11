package controllers;

import interfaces.CollectionEmployee;
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
import objects.Employee;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utils.DialogManager;

import java.io.IOException;
import java.lang.reflect.Method;

public class EmployeesController {
    private CollectionEmployee collectionEmployee = new CollectionEmployee();
    private Stage mainStage;
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EmployeeDialogController employeeDialogController;
    private Stage editDialogStage;
    private Stage newEditDialogStage;
    private ObservableList<Employee> backupList;
    OnCreateStage creating = new OnCreateStage();
    private ModalityDialogWindow modalityDialogWindow = new ModalityDialogWindow();

    @FXML
//    private CustomTextField tfSearchEmployee;
    private TextField tfSearchEmployee;
//    private TextField tfSearchEmployee;

    @FXML
    private TableView tableEmployee;

    @FXML
    private TableColumn<Employee, String> tableIdEmployee;

    @FXML
    private TableColumn<Employee, String> tableLastNameEmployee;

    @FXML
    private TableColumn<Employee, String> tableFirstNameEmployee;

    @FXML
    private TableColumn<Employee, String> tablePatronymicEmployee;

    @FXML
    private TableColumn<Employee, String> tableVacancyEmployee;

    @FXML
    private Label labelCountEmployee;

    @FXML
    private void initialize() {
        tableEmployee.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tableIdEmployee.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableLastNameEmployee.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        tableFirstNameEmployee.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        tablePatronymicEmployee.setCellValueFactory(new PropertyValueFactory<>("Patronymic"));
        tableVacancyEmployee.setCellValueFactory(new PropertyValueFactory<>("Vacancy"));

        initListener();
        collectionEmployee.fillTestDataEmployee();
        backupList = FXCollections.observableArrayList();
        backupList.addAll(collectionEmployee.getEmployeeObservableList());
        tableEmployee.setItems(collectionEmployee.getEmployeeObservableList());

        initLoader();
//        setupClearButtonField(tfSearchEmployee);
    }

    private void initListener() {
        collectionEmployee.getEmployeeObservableList().addListener((ListChangeListener<Employee>) c -> updateCountLabelEmployee());

        tableEmployee.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                employeeDialogController.setEmployee((Employee) tableEmployee.getSelectionModel().getSelectedItem());
                showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
            }
        });
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../addEmployee.fxml"));
            fxmlEdit = fxmlLoader.load();
            employeeDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCountLabelEmployee() {
        labelCountEmployee.setText("Количество сотрудников: " + collectionEmployee.getEmployeeObservableList().size());
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
        Employee selectedEmployee = (Employee) tableEmployee.getSelectionModel().getSelectedItem();
        employeeDialogController.setEmployee(selectedEmployee);

        switch (clickedButton.getId()) {
            case "btnAddEmployee":
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                employeeDialogController.setEmployee(new Employee());
                showDialog(editDialogStage);
                if (!employeeDialogController.getEmployee().getId().equals("") || !employeeDialogController.getEmployee().getLastName().equals("") ||
                        !employeeDialogController.getEmployee().getFirstName().equals("") || !employeeDialogController.getEmployee().getPatronymic().equals("") ||
                        !employeeDialogController.getEmployee().getVacancy().equals("")) {
                    collectionEmployee.add(employeeDialogController.getEmployee());
                    backupList.add(collectionEmployee.lasted());
                }
                System.out.println("add " + collectionEmployee.lasted());
                break;
            case "btnEditEmployee":
                if (!employeeIsSelected(selectedEmployee)) return;
                editDialogStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                employeeDialogController.setEmployee(selectedEmployee);
                showDialog(editDialogStage);
                collectionEmployee.getEmployeeObservableList().clear();
                collectionEmployee.getEmployeeObservableList().addAll(backupList);
//                employeeDialogController.setEmployee(selectedEmployee);
                System.out.println(selectedEmployee);
                break;
            case "btnDeleteEmployee":
                if (!employeeIsSelected(selectedEmployee)) return;
                collectionEmployee.delete(selectedEmployee);
                break;
            case "btn_seller_Empl":
                optionsForNewWindow(actionEvent, "../sellers.fxml", "Продавцы");
                break;
            case "btn_buyer_Empl":
                optionsForNewWindow(actionEvent, "../buyers.fxml", "Покупатели");
                break;
            case "btn_transaction_Empl":
                optionsForNewWindow(actionEvent, "../transaction.fxml", "Операции");
                System.out.println("AAAAAAAAAAAA");
                break;
        }
    }

    private boolean employeeIsSelected(Employee selectedEmployee) {
        if (selectedEmployee == null) {
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

    public void actionSearch(ActionEvent actionEvent) {
        collectionEmployee.getEmployeeObservableList().clear();
        for (Employee employee : backupList) {
            if (employee.getId().toLowerCase().contains(tfSearchEmployee.getText().toLowerCase()) ||
                    employee.getLastName().toLowerCase().contains(tfSearchEmployee.getText().toLowerCase()) ||
                    employee.getFirstName().toLowerCase().contains(tfSearchEmployee.getText().toLowerCase()) ||
                    employee.getPatronymic().toLowerCase().contains(tfSearchEmployee.getText().toLowerCase()) ||
                    employee.getVacancy().toLowerCase().contains(tfSearchEmployee.getText().toLowerCase()))
                collectionEmployee.getEmployeeObservableList().add(employee);
        }
        System.out.println(backupList.get(backupList.size() - 1));
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
