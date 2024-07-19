package edu.school21.printer.app;

import edu.school21.printer.logic.ImageReader;
import edu.school21.printer.logic.Args;
import com.beust.jcommander.JCommander;

public class Program {
    public static void main(String[] args) {
        Args colors = new Args();
        JCommander.newBuilder()
                .addObject(colors)
                .build()
                .parse(args);
        ImageReader ir = new ImageReader(colors, "target/resources/image.bmp");
        ir.print();
    }
}
