package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.House;
import objects.Seller;


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

        System.out.println("In seller list");
//        getSellerList().forEach(e -> System.out.println(e.toString()));
        if (getSellerList().contains(seller)) {
            sellerList.remove(seller);
        } else {

            for (int i = 0; i < sellerList.size(); i++) {
                if (sellerList.get(i).getId().equalsIgnoreCase(seller.getId()) && sellerList.get(i).getLastName().equalsIgnoreCase(seller.getLastName()) &&
                        sellerList.get(i).getFirstName().equalsIgnoreCase(seller.getFirstName()) && sellerList.get(i).getPatronymic().equalsIgnoreCase(seller.getPatronymic()) &&
                        sellerList.get(i).getBrthDate().equalsIgnoreCase(seller.getBrthDate()) && sellerList.get(i).getObjId().equalsIgnoreCase(seller.getObjId()) &&
                        sellerList.get(i).getPhone().equalsIgnoreCase(seller.getPhone())) {
                    sellerList.remove(sellerList.get(i));
//                    sellerList.remove(i);
                    break;
                }

            }
        }

        System.out.println("after rem");
        getSellerList().forEach(e -> System.out.println(e.toString()));
    }

    public ObservableList<Seller> getSellerList() {
        return sellerList;
    }

//    public ObservableList<Seller> getSellerHouse() {
//        sellerHouse.addAll(this.fillTestDataSeller());
//        if (backupListSeller.isEmpty()) sellerHouse.forEach(getSeller -> {
//            if (!backupListSeller.contains(getSeller)) backupListSeller.add(getSeller);
//        });
//        else if (!backupListSeller.containsAll(getSellerList())){
////            backupListSeller.addAll(getSellerList()); // --
//        } else {
////            getSellerList().forEach(getSeller -> {
////                if (!backupListSeller.contains(getSeller)) backupListSeller.add(getSeller);
////            });
//        }
//        System.out.println("getSellerHouse " + backupListSeller.size());
//        return backupListSeller;
//    }

    public ObservableList<Seller> fillTestDataSellerHouse() {
        sellerList.clear();
        sellerHouse.clear();
//        sellerList.addAll(getSellerList());
        if (sellerList.isEmpty()) fillTestDataSeller();
//        System.out.println("fillTestDataSellerHouse " + getSellerHouse().size());
//        fillTestDataSeller();
        for (Seller seller : sellerList) {
            for (House house : collectionHouse) {
                if (seller.getObjId().equals(house.getId()) && !sellerHouse.contains(seller)) {
                    sellerHouse.add(new Seller(seller.getId(), seller.getLastName(), seller.getFirstName(), seller.getPatronymic(), seller.getBrthDate(), seller.getObjId(), seller.getPhone()));
                }
            }
        }
        sellerHouse.forEach(h -> System.out.println(h.toString()));
        return sellerHouse;
    }

    public ObservableList<Seller> getSellerHouse() {
        return sellerHouse;
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




}
