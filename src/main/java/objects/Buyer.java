package objects;

import javafx.beans.property.SimpleStringProperty;

public class Buyer {
    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty patronymic = new SimpleStringProperty("");
    private SimpleStringProperty birthday = new SimpleStringProperty("");
    private SimpleStringProperty phone = new SimpleStringProperty("");

    public Buyer() {
    }

    public Buyer(String id, String lastName, String firstName, String patronymic, String birthday, String phone) {
        this.id = new SimpleStringProperty(id);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.birthday = new SimpleStringProperty(birthday);
        this.phone = new SimpleStringProperty(phone);
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

    public SimpleStringProperty patronymicProperty() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public SimpleStringProperty birthdayProperty() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id=" + id +
                ", lastName=" + lastName +
                ", firstName=" + firstName +
                ", patronymic=" + patronymic +
                ", birthday=" + birthday +
                ", phone=" + phone +
                '}';
    }
}
