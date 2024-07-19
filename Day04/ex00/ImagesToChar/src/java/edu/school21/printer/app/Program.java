package edu.school21.printer.app;

import edu.school21.printer.logic.ImageReader;

public class Program {
    public static void main(String[] args) {
        ImageReader ir = new ImageReader(args);
        ir.print();
    }
}
