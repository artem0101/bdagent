package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Buyer;

public class CollectionBuyer implements MainObjectInterface<Buyer> {
    private ObservableList<Buyer> buyerObservableList = FXCollections.observableArrayList();

    @Override
    public void add(Buyer buyer) {
        buyerObservableList.add(buyer);
    }

    @Override
    public void delete(Buyer buyer) {
        buyerObservableList.remove(buyer);
    }
    public ObservableList<Buyer> getBuyerObservableList() {
        return buyerObservableList;
    }

    public void print() {
        int number = 0;
        System.out.println();
        for (Buyer buyer : buyerObservableList) {
            number++;
            System.out.println(number + ") id покупателя - " + buyer.getId() + "; Фамилия -" + buyer.getLastName() +
                    "; Имя - " + buyer.getFirstName() + "; Отчество - " + buyer.getPatronymic() + "; День рождения - " +
                    buyer.getBirthday() + "; Телефон - " + buyer.getPhone());
        }
    }

    public void fillTestDataSeller() {
        buyerObservableList.add(new Buyer("1", "Санникова", "Ирина", "Анатольевна", "1990-08-01", "9992333"));
        buyerObservableList.add(new Buyer("2", "Кузнгецова", "Наталья", "Михайловна", "1990-08-01", "998411"));
        buyerObservableList.add(new Buyer("3", "Борисов", "Денис", "Юрьевичь", "1980-04-03", "475992"));
        buyerObservableList.add(new Buyer("4", "Володин", "Павел", "Михайлович", "1989-09-03", "322838"));
        buyerObservableList.add(new Buyer("5", "Сплюхин", "Геннадий", "Николаевич", "1960-03-22", "342342"));
        buyerObservableList.add(new Buyer("6", "Егорова", "Дарья", "Вячеславовна", "1985-02-02", "123984"));
        buyerObservableList.add(new Buyer("7", "Мотрохов", "Сергей", "Анатольевич", "1976-11-30", "2342291"));
        buyerObservableList.add(new Buyer("8", "Курташкин", "Евгений", "Дмитриевич", "1990-06-20", "8928901"));
    }
    public Buyer lasted() {
        return buyerObservableList.get(buyerObservableList.size() - 1);
    }


}
