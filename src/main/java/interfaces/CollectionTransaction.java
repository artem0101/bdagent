package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.*;

public class CollectionTransaction implements MainObjectInterface<Transaction> {
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
        transactionObservableList.add(new Transaction("1", "2001", "1", "4", "3", "2014-03-05", ""));
    }

    public Transaction lasted() { return transactionObservableList.get(transactionObservableList.size() - 1); }
}
