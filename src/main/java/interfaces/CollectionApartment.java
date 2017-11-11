package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Apartment;

public class CollectionApartment implements MainObjectInterface<Apartment> {
    private ObservableList<Apartment> apartmentObservableList = FXCollections.observableArrayList();

    @Override
    public void add(Apartment apartment) {
        apartmentObservableList.add(apartment);
    }

    @Override
    public void delete(Apartment apartment) {
        apartmentObservableList.remove(apartment);
    }

    public void fillTestDataApartment() {
        apartmentObservableList.add(new Apartment("10001", "Ленинский", "Фурманова", "2000000", "2", "4", "30"));
        apartmentObservableList.add(new Apartment("10002", "Первомайский", "Кижеватова", "2100000", "3", "3", "50"));
    }

    public Apartment lasted() {
        return apartmentObservableList.get(apartmentObservableList.size() - 1);
    }

    public ObservableList<Apartment> getApartmentObservableList() {
        return apartmentObservableList;
    }
}
