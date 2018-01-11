package objects;

import javafx.beans.property.SimpleStringProperty;

public class Transaction {
    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty idObj = new SimpleStringProperty("");
    private SimpleStringProperty seller = new SimpleStringProperty("");
    private SimpleStringProperty buyer = new SimpleStringProperty("");
    private SimpleStringProperty employee = new SimpleStringProperty("");
    private SimpleStringProperty date = new SimpleStringProperty("");
    private SimpleStringProperty amount = new SimpleStringProperty("");

    public Transaction() {
    }

    public Transaction(String id, String idObj, String seller, String buyer, String employee, String date, String amount) {
        this.id = new SimpleStringProperty(id);
        this.idObj = new SimpleStringProperty(idObj);
        this.seller = new SimpleStringProperty(seller);
        this.buyer = new SimpleStringProperty(buyer);
        this.employee = new SimpleStringProperty(employee);
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleStringProperty(amount);
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

    public String getIdObj() {
        return idObj.get();
    }

    public SimpleStringProperty idObjProperty() {
        return idObj;
    }

    public void setIdObj(String idObj) {
        this.idObj.set(idObj);
    }

    public String getSeller() {
        return seller.get();
    }

    public SimpleStringProperty sellerProperty() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller.set(seller);
    }

    public String getBuyer() {
        return buyer.get();
    }

    public SimpleStringProperty buyerProperty() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer.set(buyer);
    }

    public String getEmployee() {
        return employee.get();
    }

    public SimpleStringProperty employeeProperty() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee.set(employee);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }
}
