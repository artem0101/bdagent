package objects;

import javafx.beans.property.SimpleStringProperty;

public class Employee {
    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty patronymic = new SimpleStringProperty("");
    private SimpleStringProperty vacancy = new SimpleStringProperty("");

    public Employee() {
    }

    public Employee(String id, String lastName, String firstName, String patronymic, String vacancy) {
        this.id = new SimpleStringProperty(id);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.vacancy = new SimpleStringProperty(vacancy);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public SimpleStringProperty pathronymicProperty() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public String getVacancy() {
        return vacancy.get();
    }

    public SimpleStringProperty vacancyProperty() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy.set(vacancy);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName=" + lastName +
                ", firstName=" + firstName +
                ", pathronymic=" + patronymic +
                ", vacancy=" + vacancy +
                '}';
    }
}
