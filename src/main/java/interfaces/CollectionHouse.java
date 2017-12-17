package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.House;

public class CollectionHouse implements MainObjectInterface<House> {
    private ObservableList<House> houseObservableList = FXCollections.observableArrayList();

    @Override
    public void add(House house) {
        houseObservableList.add(house);
    }

    @Override
    public void delete(House house) {
        houseObservableList.remove(house);
    }

    public ObservableList<House> getHouseObservableList() {
        return houseObservableList;
    }

    public ObservableList<House> fillTestDataHouse() {
        houseObservableList.add(new House("20001", "Ленинский", "Краснознамённая", "2400000", "2", "5", "270", "70"));
        houseObservableList.add(new House("20002", "Ленинский", "Красноармейская", "2600000", "1", "4", "250", "40"));
        return houseObservableList;
    }

    public House latest() {
        return houseObservableList.get(houseObservableList.size() - 1);
    }
}
