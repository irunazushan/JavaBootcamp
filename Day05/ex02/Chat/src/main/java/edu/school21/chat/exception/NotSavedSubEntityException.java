package edu.school21.chat.exception;

public class NotSavedSubEntityException extends RuntimeException{
    public NotSavedSubEntityException() {
        super("This 'ID' doesn't exist");
    }
}
