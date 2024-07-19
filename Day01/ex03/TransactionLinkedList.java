package ex03;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TransactionLinkedList implements TransactionList {
    private List<Transaction> transactions = null;

    public TransactionLinkedList() {
        transactions = new LinkedList<>();
    }
    @Override
    public void addTransaction(Transaction tr) {
        transactions.add(tr);
    }

    @Override
    public void removeTransaction(UUID id) {
        for (Transaction el : transactions) {
            if (el.getIdentifier() == id) {
                transactions.remove(el);
                return;
            }
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public Transaction[] transformIntoArray() {
       return transactions.toArray(new Transaction[0]);
    }
}
