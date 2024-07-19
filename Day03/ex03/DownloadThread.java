package ex03;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;

public class DownloadThread implements Runnable {
    private final int threadNumber;
    private final String downloadFolder;
    private final ArrayDeque<String> urls;
    private final Object lock;

    public DownloadThread(int threadNumber, ArrayDeque<String> urls, String downloadFolder, Object lock) {
        this.threadNumber = threadNumber;
        this.downloadFolder = downloadFolder;
        this.urls = urls;
        this.lock = lock;
    }

    private void downloadFiles() {
        while (true) {
            String[] line;
            synchronized (lock) {
                if (urls.isEmpty()) {
                    break;
                }
                line = urls.poll().split(" ");
            }
            int numOfUrl = Integer.parseInt(line[0]);
            String link = line[1].trim();
            String extension = link.substring(link.lastIndexOf("."));
            try {
                System.out.println("Thread-" + threadNumber + " start download file number" + numOfUrl);
                URL url = new URL(link);
                InputStream in = url.openStream();
                FileOutputStream fos = new FileOutputStream(downloadFolder + "/file_" + numOfUrl + extension);
                int length = -1;
                byte[] buffer = new byte[1024];
                while ((length = in.read(buffer)) > -1) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
                in.close();
                System.out.println("Thread-" + threadNumber + " finish download file number" + numOfUrl);
            } catch (MalformedURLException e) {
                System.err.println("Malformed URL: " + link);
            } catch (IOException e) {
                System.err.println("IO Exception when downloading from URL: " + link);
            }
        }
    }

    @Override
    public void run() {
        downloadFiles();
    }
}
