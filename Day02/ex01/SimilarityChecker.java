package ex01;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class SimilarityChecker {
    private static final Set<String> dictionary = new TreeSet<>();

    public void runChecker(String fileName1, String fileName2) {
        String[] text1 = readFile(fileName1);
        String[] text2 = readFile(fileName2);

        double similarity = getSimilarity(text1, text2);
        System.out.printf("Similarity = %.2f\n", similarity);
    }

    private static String[] readFile(String fileName) {
        StringBuilder text = new StringBuilder();

        try (FileReader file= new FileReader(fileName)){

            int i = 0;
            while ((i = file.read()) != -1) {
                text.append((char)i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString().split("[\\p{Punct}\\s]+");
    }

    private static void writeFile() {
        try (FileWriter file = new FileWriter("ex01/dictionary.txt")) {
            int newLine = 0;
            boolean firstEl = true;
            for (String el : dictionary) {
                if (firstEl) {
                    firstEl = false;
                    file.write(el);
                } else {
                    file.write(", " + el);
                    newLine++;
                }
                if (newLine == 10) {
                    file.write("\n");
                    firstEl = true;
                    newLine = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getSimilarity(String[] text1, String[] text2) {
        Collections.addAll(dictionary, text1);
        Collections.addAll(dictionary, text2);
        writeFile();

        int[] A = new int[dictionary.size()];
        int[] B = new int[dictionary.size()];
        int ind = 0;

        for (String el : dictionary) {
            A[ind] = 0;
            B[ind] = 0;
            for (String s : text1) {
                if (s.equals(el)) A[ind] += 1;
            }
            for (String s : text2) {
                if (s.equals(el)) B[ind] += 1;
            }
            ++ind;
        }

        int numerator  = 0;
        for (int i = 0; i < A.length; ++i) {
            numerator += A[i] * B[i];
        }

        double denominator = 0;
        int sqrtA = 0;
        int sqrtB = 0;
        for (int i = 0; i < A.length; ++i) {
            sqrtA += A[i] * A[i];
            sqrtB += B[i] * B[i];
        }

        denominator = Math.sqrt(sqrtA) * Math.sqrt(sqrtB);
        return numerator / denominator;
    }
}
