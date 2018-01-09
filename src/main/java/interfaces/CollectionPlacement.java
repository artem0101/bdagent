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
        if (getPlacementObservableList().contains(placement)) {
            placementObservableList.remove(placement);
        } else {
            for (int i = 0; i < placementObservableList.size(); i++) {
                if (placementObservableList.get(i).getId().equalsIgnoreCase(placement.getId()) && placementObservableList.get(i).getDistinct().equalsIgnoreCase(placement.getDistinct()) &&
                        placementObservableList.get(i).getAddress().equalsIgnoreCase(placement.getAddress()) && placementObservableList.get(i).getPrice().equalsIgnoreCase(placement.getPrice()) &&
                        placementObservableList.get(i).getFloors().equalsIgnoreCase(placement.getFloors()) && placementObservableList.get(i).getRooms().equalsIgnoreCase(placement.getRooms()) &&
                        placementObservableList.get(i).getArea().equalsIgnoreCase(placement.getArea())) {
                    placementObservableList.remove(i);
                }
            }
        }
        placementObservableList.add(placement);
    }

    public ObservableList<Placement> getPlacementObservableList() {
        return placementObservableList;
    }

    public ObservableList<Placement> fillTestDataPlacement() {
        placementObservableList.add(new Placement("40001", "Октябрьский", "Аустрина", "1800000", "3", "8", "400"));
        placementObservableList.add(new Placement("40002", "Октябрьский", "Ладожская", "2000000", "2", "4", "350"));
        return placementObservableList;
    }

    public Placement latest() {
        return placementObservableList.get(placementObservableList.size() - 1);
    }
}
