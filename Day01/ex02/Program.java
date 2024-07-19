package ex02;

public class Program {

    public static void main(String[] args) {
        User user1 = new User("Mike", 500);
        User user2 = new User("John", 200);
        User user3 = new User("Alica", 100);
        User user4 = new User("Roman", 1000);
        User user5 = new User("Kamil", 750);
        User user6 = new User("Aleksandr", 360);
        User user7 = new User("Eva", 140);
        User user8 = new User("Ameliya", 360);
        User user9 = new User("Matvey", 705);
        User user10 = new User("Ernest", 10);
        User user11 = new User("Egor", 345);

        UsersArrayList usersList = new UsersArrayList();

        usersList.addUserToList(user1);
        usersList.addUserToList(user2);
        usersList.addUserToList(user3);
        usersList.addUserToList(user4);
        usersList.addUserToList(user5);

        System.out.println("Number of users: " + usersList.getNumberOfUsers());

        usersList.addUserToList(user6);
        usersList.addUserToList(user7);
        usersList.addUserToList(user8);
        usersList.addUserToList(user9);
        usersList.addUserToList(user10);
        usersList.addUserToList(user11);

        System.out.println("Number of users: " + usersList.getNumberOfUsers());

        System.out.println("\nGet Users by Id: ");
        for (int i = 0; i < 11; ++i) {
            System.out.println(usersList.getUserById(i + 1));
        }

        System.out.println("\nGet Users by Index: ");
        for (int i = 0; i < 11; ++i) {
            System.out.println(usersList.getUserByIndex(i));
        }

        /*
        uncomment to see the error throwing:
         */
//        System.out.println(usersList.getUserByIndex(15));
//        System.out.println(usersList.getUserById(19));

    }
}
