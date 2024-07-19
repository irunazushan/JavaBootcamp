package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;


public class ImageReader {

    final private Args colors;
    final private String pathToImage;

    public ImageReader(Args colors, String path) {
        this.pathToImage = path;
        this.colors = colors;
    }


    public void print() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(pathToImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ColoredPrinter colorPrinter = new ColoredPrinter();

        for (int i = 0; i < image.getWidth(); ++i) {
            for (int j = 0; j < image.getHeight(); ++j) {
                if (image.getRGB(j, i) == Color.WHITE.getRGB()) {
                    colorPrinter.print("  ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(colors.getWhitePxColor()));
                } else {
                    colorPrinter.print("  ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(colors.getBlackPxColor()));
                }
            }
            System.out.println();
        }
    }
}
