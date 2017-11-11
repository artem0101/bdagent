package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Placement;

public class CollectionPlacement implements MainObjectInterface<Placement> {
    private ObservableList<Placement> placementObservableList = FXCollections.observableArrayList();

    @Override
    public void add(Placement placement) {
        placementObservableList.add(placement);
    }

    @Override
    public void delete(Placement placement) {
        placementObservableList.add(placement);
    }

    public ObservableList<Placement> getPlacementObservableList() {
        return placementObservableList;
    }

    public void fillTestDataPlacement() {
        placementObservableList.add(new Placement("40001", "Октябрьский", "Аустрина", "1800000", "3", "8", "400"));
        placementObservableList.add(new Placement("40002", "Октябрьский", "Ладожская", "2000000", "2", "4", "350"));
    }

    public Placement latest() {
        return placementObservableList.get(placementObservableList.size() - 1);
    }
}
