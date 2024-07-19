package ex03;

import java.util.Arrays;
import java.util.LinkedList;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("Mike", 500);
        User user2 =  new User( "John", 200);
        User user3 =  new User( "Ernest", 1000);

        Transaction tr1 = new Transaction(user1, user2, TransferCategories.CREDIT, 50);
        Transaction tr2 = new Transaction(user3, user2, TransferCategories.DEBIT, 450);
        Transaction tr3 = new Transaction(user2, user1, TransferCategories.DEBIT, 100);

        TransactionLinkedList transactions = new TransactionLinkedList();

        transactions.addTransaction(tr1);
        transactions.addTransaction(tr2);
        transactions.addTransaction(tr3);

        user2.transactions = transactions;
        System.out.println("\nUser second transaction: ");
        System.out.println(user2.transactions.transformIntoArray()[1]);

        System.out.println("\nAll transactions: ");
        Transaction[] trArray = transactions.transformIntoArray();
        for (Transaction el : trArray) {
            System.out.println(el);
        }

        transactions.removeTransaction(tr2.getIdentifier());
        transactions.removeTransaction(tr1.getIdentifier());

        System.out.println("\nAfter deleting first and second transaction: ");
        trArray = transactions.transformIntoArray();
        for (Transaction el : trArray) {
            System.out.println(el);
        }

    }

}
