package ex03;

import ex03.DownloadThread;

import java.util.ArrayDeque;

public class ThreadsRunner {
    public static Thread[] initThreads(int amountOfThreads, ArrayDeque<String> urls, Object lock) {
        Thread[] threads = new Thread[amountOfThreads];
        for (int i = 0; i < amountOfThreads; i++) {
            threads[i] = new Thread(new DownloadThread(i + 1, urls, "ex03/downloads", lock));
        }
        return threads;
    }

    public static void startThreads(Thread[] threads) {
        for (Thread el : threads) {
            el.start();
        }

        for (Thread el : threads) {
            try {
                el.join();
            } catch (InterruptedException e) {
                el.interrupt();
            }
        }
    }
}
