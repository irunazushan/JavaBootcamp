package ex05;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Menu {

    private final TransactionsService service = new TransactionsService();
    private final Scanner sc = new Scanner(System.in);

    public void runMenu(boolean devMode) {
        int choice = 0;
        while (choice != 7) {
            System.out.println("1. Add a user\n" +
                    "2. View user balances\n" +
                    "3. Perform a transfer\n" +
                    "4. View all transactions for a specific user\n" +
                    "5. DEV – remove a transfer by ID\n" +
                    "6. DEV – check transfer validity\n" +
                    "7. Finish execution\n");
            try {
                choice = Integer.parseInt(readInput(1)[0]);
                executeProgram(choice, devMode);
            } catch (RuntimeException e) {
                System.err.println("Incorrect choice, try again");
            }
        }
    }

    private void executeProgram(int choice, boolean devMode) {

        switch (choice) {
            case 1:
                addUserChoice();
                break;
            case 2:
                viewUserBalanceChoice();
                break;
            case 3:
                performTransferChoice();
                break;
            case 4:
                viewUserTransactionsChoice();
                break;
            case 5:
                if (devMode) {
                    removeTransferChoice();
                } else {
                    System.out.println("Removing is only available in development mode");
                }
                break;
            case 6:
                if (devMode) {
                    checkTransferValidityChoice();
                } else {
                    System.out.println("Transfer cheking is only available in development mode");
                }
                break;
        }
        System.out.println("---------------------------------------------------------");
    }

    private String[] readInput(int expectedNumOfElements) {
        String input = sc.nextLine();
        String[] result = input.split("\\s+");
        if (result.length != expectedNumOfElements) {
            throw new RuntimeException("Incorrect input, try again");
        }
        return result;
    }

    private void addUserChoice() {
        System.out.println("Enter a user name and a balance");
        String name = "";
        int balance = 0;
        try {
            String[] input = readInput(2);
            name = input[0];
            balance = Integer.parseInt(input[1]);
            User user = new User(name, balance);
            service.addUser(user);
            System.out.println("User with id = " + user.getIdentifier() + " is added");
        } catch (RuntimeException e) {
            System.err.println(e);
        }
    }

    private void viewUserBalanceChoice() {
        System.out.println("Enter a user ID");
        int id = 0;
        try {
            id = Integer.parseInt(readInput(1)[0]);
            User user = service.getUser(id);
            service.addUser(user);
            System.out.println(user.getName() + " - " + user.getBalance());
        } catch (RuntimeException e) {
            System.err.println(e);
        }
    }

    private void performTransferChoice() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        int id1 = 0;
        int id2 = 0;
        int amount = 0;
        try {
            String[] input = readInput(3);
            id1 = Integer.parseInt(input[0]);
            id2 = Integer.parseInt(input[1]);
            amount = Integer.parseInt(input[2]);
            service.performTransfer(id1, id2, amount);
            System.out.println("The transfer is completed");
        } catch (RuntimeException e) {
            System.err.println(e);
        }
    }

    private void viewUserTransactionsChoice() {
        System.out.println("Enter a user ID");
        int id = 0;
        try {
            id = Integer.parseInt(readInput(1)[0]);
            Transaction[] userTransactions = service.getUserTransactions(id);
            for (Transaction el : userTransactions) {
                User recipient = el.getSender();
                int amount = el.getTransferAmount();
                String fromTo = el.getTransferCategory() == TransferCategories.DEBIT ? "To" : "From";
                System.out.printf("%s %s(id = %d) %d with id = %s\n", fromTo, recipient.getName(), recipient.getIdentifier(), amount, el.getIdentifier());
            }
        } catch (RuntimeException e) {
            System.err.println(e);
        }
    }

    private void removeTransferChoice() {
        System.out.println("Enter a user ID and a transfer ID");
        int userId = 0;
        UUID transferId;
        boolean transactionFound = false;
        try {
            String[] input = readInput(2);
            userId = Integer.parseInt(input[0]);
            transferId = UUID.fromString(input[1]);
            Transaction[] userTransactions = service.getUserTransactions(userId);
            for (Transaction el : userTransactions) {
                if (el.getIdentifier().equals(transferId)) {
                    transactionFound = true;
                    User recipient = el.getSender();
                    String name = recipient.getName();
                    int amount = el.getTransferAmount();
                    String fromTo = el.getTransferCategory() == TransferCategories.DEBIT ? "To" : "From";
                    service.removeTransaction(userId, el.getIdentifier());
                    System.out.printf("Transfer %s %s(id = %d) %d removed\n", fromTo, name, userId, amount);
                }
            }
            if (!transactionFound) {
                System.out.println("Transaction was not found");
            }
        } catch (RuntimeException e) {
            System.err.println(e);
        }
    }

    private void checkTransferValidityChoice() {
        System.out.println("Check results:\n");
        try {
            Transaction[] userTransactions = service.checkValidity();
            ;
            for (Transaction el : userTransactions) {
                User recipient = el.getSender();
                User sender = el.getRecipient();
                int amount = el.getTransferAmount();
                String fromTo = el.getTransferCategory() == TransferCategories.DEBIT ? "to" : "from";
                System.out.printf("%s(id = %d) has an unacknowledged transfer id = %s %s(id = %d) for %d\n", recipient.getName(), recipient.getIdentifier(), fromTo, sender.getName(), sender.getIdentifier(), amount);
            }
        } catch (RuntimeException e) {
            System.err.println(e);
        }
    }
}
