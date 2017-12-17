package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.House;
import objects.Seller;

import java.util.Collection;

public class CollectionSeller implements MainObjectInterface<Seller> {
    private ObservableList<Seller> sellerList = FXCollections.observableArrayList();
    private ObservableList<Seller> sellerHouse = FXCollections.observableArrayList();
    private ObservableList<House> collectionHouse = FXCollections.observableArrayList(new CollectionHouse().fillTestDataHouse());
//    private House house = new House();

    @Override
    public void add(Seller seller) {
        sellerList.add(seller);
    }

    @Override
    public void delete(Seller seller) {
        sellerList.remove(seller);
    }

    public ObservableList<Seller> getSellerList() {
        return sellerList;
    }

    public void print() {
        int number = 0;
        System.out.println();
        for (Seller seller : sellerList) {
            number++;
            System.out.println(number + ") id продавца - " + seller.getId() + "; Фамилия -" + seller.getLastName() +
                    "; Имя - " + seller.getFirstName() + "; Отчество - " + seller.getPatronymic() + "; День рождения - " +
                    seller.getBrthDate() + "; id объекта - " + seller.getObjId() + "; Телефон - " + seller.getPhone());
        }
    }

    public Seller lasted() { return sellerList.get(sellerList.size() - 1); }

    public ObservableList<Seller> fillTestDataSeller() {
        sellerList.add(new Seller("1", "Санникова", "Ирина", "Анатольевна", "1990-08-01", "20001", "9992333"));
        sellerList.add(new Seller("2", "Кузнгецова", "Наталья", "Михайловна", "1990-08-01", "10001", "998411"));
        sellerList.add(new Seller("3", "Борисов", "Денис", "Юрьевичь", "1980-04-03", "30001", "475992"));
        sellerList.add(new Seller("4", "Володин", "Павел", "Михайлович", "1989-09-03", "20002", "322838"));
        sellerList.add(new Seller("5", "Сплюхин", "Геннадий", "Николаевич", "1960-03-22", "10002", "342342"));
        sellerList.add(new Seller("6", "Егорова", "Дарья", "Вячеславовна", "1985-02-02", "40001", "123984"));
        sellerList.add(new Seller("7", "Мотрохов", "Сергей", "Анатольевич", "1976-11-30", "30002", "2342291"));
        sellerList.add(new Seller("8", "Курташкин", "Евгений", "Дмитриевич", "1990-06-20", "40002", "8928901"));
        return sellerList;
    }

    public ObservableList<Seller> fillTestDataSellerHouse() {
        sellerHouse.clear();
        sellerList.clear();
//        fillTestDataSeller();
        for (Seller seller : fillTestDataSeller()) {
//            System.out.println("111111" + seller);
            for (House house : collectionHouse) {
                if (seller.getObjId().equals(house.getId())) {
                    sellerHouse.add(new Seller(seller.getId(), seller.getLastName(), seller.getFirstName(), seller.getLastName(), seller.getPatronymic(), seller.getObjId(), seller.getPhone()));
//                    System.out.println("EEEEE");
                }
            }
        }
//        for (Seller seller : sellerHouse) System.out.println(seller.toString());
        return sellerHouse;
    }


}
