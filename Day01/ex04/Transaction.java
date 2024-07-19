
package ex04;

import java.util.UUID;

public class Transaction {

    private final UUID identifier;
    private final User recipient;
    private final User sender;

    private final TransferCategories transferCategory;
    private final int transferAmount;

    public Transaction(User recipient, User sender, TransferCategories transferCategory, int transferAmount) {
        if (transferAmount < 0) {
            System.err.println("Amount of money for transferring cannot be negative");
            System.exit(-1);
        }
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
        applyTransaction();
    }

    public Transaction(Transaction toInverse) {
        this.identifier = toInverse.getIdentifier();
        this.recipient = toInverse.getSender();
        this.sender = toInverse.getRecipient();
        this.transferCategory = toInverse.transferCategory == TransferCategories.CREDIT ? TransferCategories.DEBIT : TransferCategories.CREDIT;
        this.transferAmount = -toInverse.getTransferAmount();
    }

    private void applyTransaction() {
        if (transferCategory == TransferCategories.CREDIT) {
            recipient.applyCredit(transferAmount);
            sender.applyDebit(transferAmount);
        } else if (transferCategory == TransferCategories.DEBIT) {
            sender.applyCredit(transferAmount);
            recipient.applyDebit(transferAmount);
        }
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }
    public int getTransferAmount() {
        return transferAmount;
    }

    @Override
    public String toString() {
        return "Transcation{" +
                "identifier=" + identifier +
                ", recipient=" + recipient +
                ", sender=" + sender +
                ", transferCategory=" + transferCategory +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
