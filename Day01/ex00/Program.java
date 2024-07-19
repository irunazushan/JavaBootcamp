package ex00;

public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, "Mike", 500);
        User user2 =  new User(2, "John", 200);

        System.out.println(user1);
        System.out.println(user2);

        Transaction transaction1 = new Transaction(user1, user2, TransferCategories.DEBIT, 100);
        System.out.println(transaction1);
    }

}
