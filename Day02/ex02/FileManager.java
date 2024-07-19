package ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileManager {
    private static String currPath = "";

    private static final Scanner sc = new Scanner(System.in);

    FileManager(String startFolder) {
        currPath = startFolder;
        File check = new File(currPath);
        if (!check.exists()) {
            System.out.println("Incorrect current-folder");
            System.exit(-1);
        }
    }

    public void runFileManager() {
        String[] commandParser = sc.nextLine().split(" ");
        while (!commandParser[0].equals("exit")) {
            if (commandParser[0].equals("ls")) {
                lsCommand(currPath);
            } else if (commandParser[0].equals("cd") && commandParser.length == 2) {
                cdCommand(commandParser[1]);
            } else if (commandParser[0].equals("mv") && commandParser.length == 3) {
                mvCommand(commandParser[1], commandParser[2]);
            } else {
                System.err.println("Incorrect command, try again");
            }
            commandParser = sc.nextLine().split(" ");
        }
    }

    private static double getSizeInKB(File file) {
        return file.length() / 1024.0;
    }

    private static void lsCommand(String currPath) {
        File currDirectory = new File(currPath);
        File[] files = currDirectory.listFiles();

        if (files != null) {
            for (File el : files) {
                System.out.printf("%s %.2f KB\n", el.getName(), getSizeInKB(el));
            }
        }
    }

    private static void cdCommand(String folder) {
        String isValid = currPath + "/" + folder;
        if (Files.isDirectory(Paths.get(isValid))) {
            currPath = Paths.get(isValid).normalize().toString();
            System.out.println(currPath);
        } else {
            System.err.println("No such directory: " + isValid);
        }
    }

    private static void mvCommand(String from, String to) {
        String source = currPath + "/" + from;
        String destination = currPath + "/" + to;
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(destination);
        try {
            if (Files.isDirectory(destinationPath)) {
                Files.move(sourcePath, Paths.get(destination + "/" + from));
            } else {
                Files.move(sourcePath, destinationPath);
            }
        } catch (IOException e) {
            System.err.println("Something go wrong");
        }
    }
}
