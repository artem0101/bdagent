package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Employee;
import utils.DialogManager;

public class EmployeeDialogController {

    @FXML
    private TextField tf_employee;

    @FXML
    private TextField tf_surname;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_patronymic;

    @FXML
    private TextField tf_post;

    private Employee employee;

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setEmployee(Employee employee) {
        if (employee == null) return;
        this.employee = employee;
        tf_employee.setText(employee.getId());
        tf_surname.setText(employee.getLastName());
        tf_name.setText(employee.getFirstName());
        tf_patronymic.setText(employee.getPatronymic());
        tf_post.setText(employee.getVacancy());
    }

    public void actionSave(ActionEvent actionEvent) {
        if (tf_employee.getText().equals("") || tf_surname.getText().equals("") || tf_name.getText().equals("") ||
                tf_patronymic.getText().equals("") || tf_post.getText().equals("")) {
            DialogManager.showInfoDialog("Ошибка", "Введены не все данные");
        } else {
            System.out.println("\nid - " + tf_employee.getText() + ";\nИмя - " + tf_surname.getText() + ";\nФамилия - " +
                    tf_name.getText() +";\nОтчество - " + tf_patronymic.getText() + ";\nДолжность - " + tf_post.getText() + ";\n");
            System.out.println(employee.toString());
            employee.setId(tf_employee.getText());
            employee.setLastName(tf_surname.getText());
            employee.setFirstName(tf_name.getText());
            employee.setPatronymic(tf_patronymic.getText());
            employee.setVacancy(tf_post.getText());
            actionClose(actionEvent);
        }
    }

    public Employee getEmployee() {
        return employee;
    }
}
