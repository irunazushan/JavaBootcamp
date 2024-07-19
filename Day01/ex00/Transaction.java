
package ex00;
import java.util.UUID;

enum TransferCategories {
    DEBIT, CREDIT
}
public class Transaction {

    private final UUID identifier;
    private final User recipient;
    private final User sender;
    private final TransferCategories transferCategory;
    private final int transferAmount;

    public Transaction(User recipient, User sender, TransferCategories transferCategory, int transferAmount) {
        if (transferAmount < 0) {
            System.err.println("Amount of money for transfering cannot be negative");
            System.exit(-1);
        }
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
        applyTransaction();
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
