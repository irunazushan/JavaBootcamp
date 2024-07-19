package edu.school21.chat.app;

import java.time.LocalDateTime;

public class Program {

    public static void main(String[] args) {
        DBRunner dbr = new DBRunner();
        dbr.updateMessage(5L, -1L, -1L, "Bye", LocalDateTime.now());
    }
}
