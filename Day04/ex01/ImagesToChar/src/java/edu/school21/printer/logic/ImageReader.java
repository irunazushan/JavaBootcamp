package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReader {
    final private String charForWhitePx;
    final private String charForBlackPx;
    final private String pathToImage;

    public ImageReader(String[] args) {
        if (!checkValidity(args)) {
            throw new RuntimeException("Incorrect arguments are given");
        }
        this.pathToImage = "target/resources/image.bmp";
        this.charForWhitePx = args[0];
        this.charForBlackPx = args[1];
    }

    public void print() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(pathToImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
        for (int i = 0; i < image.getWidth(); ++i) {
            for (int j = 0; j < image.getHeight(); ++j) {
                String symbol = image.getRGB(j,i) == Color.WHITE.getRGB() ? charForWhitePx : charForBlackPx;
                System.out.printf("%s", symbol);
            }
            System.out.println();
        }
    }

    private boolean checkValidity(String[] args) {
        if (args.length != 2) {
            return false;
        }
        if (args[0].length() != 1 || args[1].length() != 1) {
            return false;
        }
        return true;
    }
}
