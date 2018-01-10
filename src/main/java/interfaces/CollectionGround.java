package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Ground;

public class CollectionGround implements MainObjectInterface<Ground> {
    private ObservableList<Ground> groundObservableList = FXCollections.observableArrayList();

    @Override
    public void add(Ground ground) {
        groundObservableList.add(ground);
    }

    @Override
    public void delete(Ground ground) {
        groundObservableList.remove(ground);
    }

    public ObservableList<Ground> getGroundObservableList() {
        return groundObservableList;
    }

    public ObservableList<Ground> fillTestDataGround() {
        groundObservableList.add(new Ground("30001", "Октябрьский", "Компрессорная", "2300000", "300"));
        groundObservableList.add(new Ground("30002", "Железнодорожный", "Клары-Цеткин", "2100000", "400"));
        return groundObservableList;
    }

    public Ground latest() {
        return groundObservableList.get(groundObservableList.size() - 1);
    }
}
