package edu.school21.printer;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import edu.school21.renderer.Renderer;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }
    @Override
    public void print(String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String dateTime = dtf.format(LocalDateTime.now());
        renderer.printToConsole(dateTime + " " + message);
    }
}
