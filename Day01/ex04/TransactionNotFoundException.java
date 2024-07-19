package ex04;

public class TransactionNotFoundException extends RuntimeException{
    TransactionNotFoundException() {
        super("Transaction not found");
    }
}
