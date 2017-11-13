package objects;

import javafx.beans.property.SimpleStringProperty;

public class Seller {
    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty patronymic = new SimpleStringProperty("");
    private SimpleStringProperty birthday = new SimpleStringProperty("");
    private SimpleStringProperty objId = new SimpleStringProperty("");
    private SimpleStringProperty phone = new SimpleStringProperty("");

    public Seller() {
    }

    public Seller(String id, String lastName, String firstName,
                  String patronymic, String birthDate, String objId,
                  String phone) {
        this.id = new SimpleStringProperty(id);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.birthday = new SimpleStringProperty(birthDate);
        this.objId = new SimpleStringProperty(objId);
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

    public String getBrthDate() {
        return birthday.get();
    }

    public SimpleStringProperty brthDateProperty() {
        return birthday;
    }

    public void setBrthDate(String brthDate) {
        this.birthday.set(brthDate);
    }

    public String getObjId() {
        return objId.get();
    }

    public SimpleStringProperty objIdProperty() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId.set(objId);
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
        return "Seller{" +
                "id=" + id +
                ", lastName=" + lastName +
                ", firstName=" + firstName +
                ", patronymic=" + patronymic +
                ", birthDate=" + birthday +
                ", objId=" + objId +
                ", phone=" + phone +
                '}';
    }
}
