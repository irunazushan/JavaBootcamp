package ex04;

import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();

        User[] users = new User[5];

        users[0] = new User("Mike", 500);
        users[1] = new User("John", 200);
        users[2]  = new User("Alica", 100);
        users[3] = new User("Roman", 1000);
        users[4] = new User("Kamil", 750);

        for (User user : users) {
            service.addUser(user);
        }

        System.out.println("Balance until transaction: ");
        System.out.println(service.getUserBalance(4));
        System.out.println(service.getUserBalance(3));
        System.out.println(service.getUserBalance(2));

        service.performTransfer(4, 2, 300);
        service.performTransfer(4, 3, 300);

        System.out.println("\nBalance after transaction: ");
        System.out.println(service.getUserBalance(4));
        System.out.println(service.getUserBalance(3));
        System.out.println(service.getUserBalance(2));

        System.out.println("\nTransaction of 4th user: ");
        Arrays.stream(service.getUserTransactions(4)).forEach(System.out::println);
        System.out.println("\nTransaction of 2d user: ");
        Arrays.stream(service.getUserTransactions(2)).forEach(System.out::println);
        System.out.println("\nTransaction of 3d user: ");
        Arrays.stream(service.getUserTransactions(3)).forEach(System.out::println);

        System.out.println("\nDelete first transaction of 4th user: ");
        System.out.println(service.getUserTransactions(4)[0]);
        service.removeTransaction(4, service.getUserTransactions(4)[0].getIdentifier());

        System.out.println("\nInvalid transactions: ");
        Arrays.stream(service.checkValidity()).forEach(System.out::println);
    }
}
