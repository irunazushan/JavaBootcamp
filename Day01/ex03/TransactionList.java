package ex03;

import java.util.UUID;

public interface TransactionList {
    public void addTransaction(Transaction tr);
    public void removeTransaction(UUID id);
    public Transaction[] transformIntoArray();

}
