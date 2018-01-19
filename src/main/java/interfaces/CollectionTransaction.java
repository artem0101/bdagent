package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.*;

public class  CollectionTransaction implements MainObjectInterface<Transaction> {
    private ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList();

    @Override
    public void add(Transaction transaction) {
        transactionObservableList.add(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionObservableList.remove(transaction);
    }

    public ObservableList<Transaction> getTransactionObservableList() {
        return transactionObservableList;
    }

    public void fillTestDataTransaction() {
        transactionObservableList.add(new Transaction("1", "20001", "1", "4", "3", "2014-03-05", "2400000"));
        transactionObservableList.add(new Transaction("2", "30001", "3", "5", "1", "2015-06-20", "2300000"));
        transactionObservableList.add(new Transaction("3", "10001", "5", "3", "2", "2015-07-05", "2000000"));
        transactionObservableList.add(new Transaction("4", "40002", "8", "1", "4", "2014-08-11", "2000000"));


//        transactionObservableList.add(new Transaction("1", "20001", "20001", "4", "3", "2014-03-05", "2400000"));
//        transactionObservableList.add(new Transaction("2", "30001", "30001", "5", "1", "2015-06-20", "2300000"));
//        transactionObservableList.add(new Transaction("3", "10001", "10001", "3", "2", "2015-07-05", "2000000"));
//        transactionObservableList.add(new Transaction("4", "40002", "40002", "1", "4", "2014-08-11", "2000000"));
    }

    public Transaction lasted() { return transactionObservableList.get(transactionObservableList.size() - 1); }
}
