package edu.school21.chat.app;

public class Program {

    public static void main(String[] args) {
        DBRunner dbr = new DBRunner();
        dbr.findAll(3, 1);
    }
}
