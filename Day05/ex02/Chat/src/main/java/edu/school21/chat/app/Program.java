package edu.school21.chat.app;

public class Program {

    public static void main(String[] args) {
        DBRunner dbr = new DBRunner();
        dbr.insertMessage(2L,5L, "From user 2 in Room5 message from Java!");
    }
}
