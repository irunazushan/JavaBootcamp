package ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignatureReader {
    private static final Map<String, String> signatures = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);

    public void runSignatureReader() {
        String fileName = sc.nextLine();
        while (!fileName.equals("42")) {
            String fileSignature = readFileSignature(fileName);
            processSignature(fileSignature);
            fileName = sc.nextLine();
        }
        sc.close();
    }
    public void initSignatures(String fileWithSignatures) {
        String[] theSignature = new String[2];
        StringBuilder buff = new StringBuilder();
        try (FileInputStream inputFile = new FileInputStream(fileWithSignatures)) {
            int i = -1;
            while ((i = inputFile.read()) != -1) {
                if (i != '\n') {
                    buff.append((char)i);
                } else {
                    theSignature = buff.toString().split(", ");
                    signatures.put(theSignature[1], theSignature[0]);
                    buff.setLength(0);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private static String readFileSignature(String fileName) {
        StringBuilder buff = new StringBuilder();
        try (FileInputStream inputFile = new FileInputStream(fileName)) {
            int i = -1;
            int amountOfSymbols = 0;
            while ((i = inputFile.read()) != -1 && (amountOfSymbols < 8)) {
                buff.append(String.format("%02X ", i));
                amountOfSymbols++;
                if (signatures.containsKey(buff.toString().trim())) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return buff.toString().trim();
    }

    private static void processSignature(String fileSignature) {
        try(FileOutputStream outputFile = new FileOutputStream("ex00/result.txt", true)) {
            if (signatures.containsKey(fileSignature)) {
                outputFile.write((signatures.get(fileSignature) + "\n").getBytes());
                System.out.println("PROCESSED");
            } else {
                System.out.println("UNDEFINED");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
