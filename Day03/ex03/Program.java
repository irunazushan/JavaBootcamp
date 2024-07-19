package ex03;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Scanner;

import static ex03.ThreadsRunner.initThreads;
import static ex03.ThreadsRunner.startThreads;

public class Program {
    private static final String THREADS_COUNT_PREFIX = "--threadsCount=";
    private static final Object lock = new Object();

    public static void main(String[] args) {
        if (!checkValidity(args)) {
            System.err.println("Invalid arguments are given");
            return;
        }

        int amountOfThreads = getNumOfThreads(args);

        final Scanner sc;
        try {
            sc = new Scanner(new FileInputStream("ex03/files_urls.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayDeque<String> urls = new ArrayDeque<>();
        while (sc.hasNext()) {
            String link = sc.nextLine().trim();
            urls.add(link);
        }
        sc.close();

        Thread[] threads = initThreads(amountOfThreads, urls, lock);
        startThreads(threads);
    }

    private static boolean checkValidity(String[] args) {
        return args.length == 1 && args[0].startsWith(THREADS_COUNT_PREFIX);
    }

    private static int getNumOfThreads(String[] args) {
        return Integer.parseInt(args[0].substring(THREADS_COUNT_PREFIX.length()));
    }
}

