package ex05;

import java.util.UUID;

public class TransactionsService {
    private UserList userList = null;

    public TransactionsService() {
        userList = new UsersArrayList();
    }

    public void addUser(User user) {
        userList.addUserToList(user);
    }

    public User getUser(int id) {
        return userList.getUserById(id);
    }

    public int getUserBalance(int id) {
        User user = userList.getUserById(id);
        return user.getBalance();
    }

    public void performTransfer(int userId1, int userId2, int amount) {
        if (userId1 == userId2) {
            System.err.println("Transaction is invalid");
            throw new IllegalTransactionException();
        }
        User sender = userList.getUserById(userId1);
        if (sender.getBalance() < amount)  {
            throw new IllegalTransactionException();
        }
        User recipient = userList.getUserById(userId2);
        Transaction credit = new Transaction(recipient, sender, TransferCategories.CREDIT, amount);
        Transaction debit = new Transaction(credit);
        recipient.transactions.addTransaction(credit);
        sender.transactions.addTransaction(debit);
    }

    public Transaction[] getUserTransactions(int userId1) {
        User user = userList.getUserById(userId1);
        return user.transactions.transformIntoArray();
    }

    public void removeTransaction(int userId, UUID transactionId) {
        User user = userList.getUserById(userId);
        user.transactions.removeTransaction(transactionId);
    }

    public Transaction[] checkValidity() {
        Transaction[] allTransactions = getAllTransactions();
        TransactionLinkedList result = new TransactionLinkedList();
        boolean isPaired = false;
        for (int i = 0; i < allTransactions.length - 1; ++i) {
            for (int j = i + 1; j < allTransactions.length; ++j) {
                if (allTransactions[i].getIdentifier().equals(allTransactions[j].getIdentifier())) {
                    isPaired = true;
                    break;
                }
            }
            if (!isPaired) {
                result.addTransaction(allTransactions[i]);
            } else {
                isPaired = false;
            }
        }
        if (allTransactions.length == 1) {
            result.addTransaction(allTransactions[0]);
        }
        return result.transformIntoArray();
    }

    private Transaction[] getAllTransactions() {
        TransactionLinkedList allTransactions = new TransactionLinkedList();
        for (int i = 0; i < userList.getNumberOfUsers(); ++i) {
            User user = userList.getUserByIndex(i);
            for (Transaction el : user.transactions.transformIntoArray()) {
                allTransactions.addTransaction(el);
            }
        }
        return allTransactions.transformIntoArray();
    }
}

