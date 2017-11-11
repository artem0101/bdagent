package objects;

import javafx.beans.property.SimpleStringProperty;

public class House {
    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty distinct = new SimpleStringProperty("");
    private SimpleStringProperty address = new SimpleStringProperty("");
    private SimpleStringProperty price = new SimpleStringProperty("");
    private SimpleStringProperty floors = new SimpleStringProperty("");
    private SimpleStringProperty rooms = new SimpleStringProperty("");
    private SimpleStringProperty area_ground = new SimpleStringProperty("");
    private SimpleStringProperty area_house = new SimpleStringProperty("");

    public House() {
    }

    public House(String id, String distinct, String address, String price, String floors, String rooms,
                 String area_ground, String area_house) {
        this.id = new SimpleStringProperty(id);
        this.distinct = new SimpleStringProperty(distinct);
        this.address = new SimpleStringProperty(address);
        this.price = new SimpleStringProperty(price);
        this.floors = new SimpleStringProperty(floors);
        this.rooms = new SimpleStringProperty(rooms);
        this.area_ground = new SimpleStringProperty(area_ground);
        this.area_house = new SimpleStringProperty(area_house);
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

    public String getFloors() {
        return floors.get();
    }

    public SimpleStringProperty floorsProperty() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors.set(floors);
    }

    public String getRooms() {
        return rooms.get();
    }

    public SimpleStringProperty roomsProperty() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms.set(rooms);
    }

    public String getArea_ground() {
        return area_ground.get();
    }

    public SimpleStringProperty area_groundProperty() {
        return area_ground;
    }

    public void setArea_ground(String area_ground) {
        this.area_ground.set(area_ground);
    }

    public String getArea_house() {
        return area_house.get();
    }

    public SimpleStringProperty area_houseProperty() {
        return area_house;
    }

    public void setArea_house(String area_house) {
        this.area_house.set(area_house);
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", distinct=" + distinct +
                ", address=" + address +
                ", price=" + price +
                ", floors=" + floors +
                ", rooms=" + rooms +
                ", area_ground=" + area_ground +
                ", area_house=" + area_house +
                '}';
    }
}
