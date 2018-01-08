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
        getHouseObservableList().forEach(h -> System.out.println(h.toString()));

        if (getHouseObservableList().contains(house)) {
            houseObservableList.remove(house);
        } else {
            for (int i = 0; i < houseObservableList.size(); i++) {
                if (houseObservableList.get(i).getId().equalsIgnoreCase(house.getId()) && houseObservableList.get(i).getDistinct().equalsIgnoreCase(house.getDistinct()) &&
                        houseObservableList.get(i).getAddress().equalsIgnoreCase(house.getAddress()) && houseObservableList.get(i).getPrice().equalsIgnoreCase(house.getPrice()) &&
                        houseObservableList.get(i).getFloors().equalsIgnoreCase(house.getFloors()) && houseObservableList.get(i).getRooms().equalsIgnoreCase(house.getRooms()) &&
                        houseObservableList.get(i).getArea_ground().equalsIgnoreCase(house.getArea_ground()) && houseObservableList.get(i).getArea_house().equalsIgnoreCase(house.getArea_house())) {
                    houseObservableList.remove(i);
                }
            }
            System.out.println("in house " + houseObservableList.size());
            houseObservableList.forEach(h -> System.out.println(h.toString()));
        }
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
