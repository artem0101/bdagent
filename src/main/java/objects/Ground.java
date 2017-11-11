package objects;

import javafx.beans.property.SimpleStringProperty;

public class Ground {
    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty distinct = new SimpleStringProperty("");
    private SimpleStringProperty address = new SimpleStringProperty("");
    private SimpleStringProperty price = new SimpleStringProperty("");
    private SimpleStringProperty area = new SimpleStringProperty("");

    public Ground() {
    }

    public Ground(String id, String distinct, String address, String price, String area) {
        this.id = new SimpleStringProperty(id);
        this.distinct = new SimpleStringProperty(distinct);
        this.address = new SimpleStringProperty(address);
        this.price = new SimpleStringProperty(price);
        this.area = new SimpleStringProperty(area);
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

    public String getDistinct() {
        return distinct.get();
    }

    public SimpleStringProperty distinctProperty() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct.set(distinct);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getArea() {
        return area.get();
    }

    public SimpleStringProperty areaProperty() {
        return area;
    }

    public void setArea(String area) {
        this.area.set(area);
    }

    @Override
    public String toString() {
        return "Ground{" +
                "id=" + id +
                ", distinct=" + distinct +
                ", address=" + address +
                ", price=" + price +
                ", area=" + area +
                '}';
    }
}
