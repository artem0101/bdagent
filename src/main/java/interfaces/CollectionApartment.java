package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Apartment;

public class CollectionApartment implements MainObjectInterface<Apartment> {
    private ObservableList<Apartment> apartmentObservableList = FXCollections.observableArrayList();

    @Override
    public void add(Apartment apartment) {
        if (getApartmentObservableList().contains(apartment)) {
            apartmentObservableList.remove(apartment);
        } else {
            for (int i = 0; i < apartmentObservableList.size(); i++) {
                if (apartmentObservableList.get(i).getId().equalsIgnoreCase(apartment.getId()) && apartmentObservableList.get(i).getDistinct().equalsIgnoreCase(apartment.getDistinct()) &&
                        apartmentObservableList.get(i).getAddress().equalsIgnoreCase(apartment.getAddress()) && apartmentObservableList.get(i).getPrice().equalsIgnoreCase(apartment.getPrice()) &&
                        apartmentObservableList.get(i).getFloor().equalsIgnoreCase(apartment.getFloor()) && apartmentObservableList.get(i).getRooms().equalsIgnoreCase(apartment.getRooms()) &&
                        apartmentObservableList.get(i).getArea().equalsIgnoreCase(apartment.getArea())) {
                    apartmentObservableList.remove(i);
                }
            }
        }
    }

    @Override
    public void delete(Apartment apartment) {
        apartmentObservableList.remove(apartment);
    }

    public ObservableList<Apartment> fillTestDataApartment() {
        apartmentObservableList.add(new Apartment("10001", "Ленинский", "Фурманова", "2000000", "2", "4", "30"));
        apartmentObservableList.add(new Apartment("10002", "Первомайский", "Кижеватова", "2100000", "3", "3", "50"));
        return apartmentObservableList;
    }

    public Apartment lasted() {
        return apartmentObservableList.get(apartmentObservableList.size() - 1);
    }

    public ObservableList<Apartment> getApartmentObservableList() {
        return apartmentObservableList;
    }
}
