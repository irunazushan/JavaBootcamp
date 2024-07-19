package ex00;
public class User {
    private final int identifier;
    private final String name;
    private int balance;

    public User(int identifier, String name, int balance) {
        this.identifier = identifier;
        this.name = name;
        if (balance < 0) {
            System.out.println("Initial balance cannot be zero");
            System.exit(-1);
        }
        this.balance = balance;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }
    private int setBalance() {
        return balance;
    }

    public void applyDebit(int amount) {
        if (balance < 0) {
            System.out.println("You cannot send negative amount of money");
            System.exit(-1);
        }
        balance -= amount;
    }

    public void applyCredit(int amount) {
        if (balance < 0) {
            System.out.println("You cannot get negative amount of money");
            System.exit(-1);
        }
        balance += amount;
    }

    @Override
    public String toString() {
        return "User{" +
                " identifier=" + identifier +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
