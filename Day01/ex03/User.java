package ex03;

import ex01.UserIdsGenerator;

public class User {
    private final int identifier;
    private final String name;
    private int balance;

    public TransactionLinkedList transactions;

    public User(String name, int balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        if (balance < 0) {
            System.out.println("Initial balance cannot be zero");
            System.exit(-1);
        }
        this.balance = balance;
        transactions = new TransactionLinkedList();
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
