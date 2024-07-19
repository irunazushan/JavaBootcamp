package edu.school21.printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = "--white", description = "A parameter for white color setting")
    String whitePxColor;
    @Parameter(names = "--black", description = "A parameter for black color setting")

    String blackPxColor;

    public String getWhitePxColor() {
        return whitePxColor;
    }

    public String getBlackPxColor() {
        return blackPxColor;
    }
}
