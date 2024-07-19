package ex05;

public class TransactionNotFoundException extends RuntimeException{
    TransactionNotFoundException() {
        super("Transaction not found");
    }
}
