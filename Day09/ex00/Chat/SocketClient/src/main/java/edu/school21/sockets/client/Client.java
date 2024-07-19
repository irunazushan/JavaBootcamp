package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private final Socket s ;
    private final PrintWriter pw;
    private final InputStreamReader in;
    private final Scanner sc;
    private final BufferedReader buff;

    public Client(int port) throws IOException {
        s = new Socket("localhost", port);
        in = new InputStreamReader(s.getInputStream());
        buff = new BufferedReader(in);
        pw = new PrintWriter(s.getOutputStream());
        sc = new Scanner(System.in);
    }
    public void run() throws IOException {
        while (true) {
            String str = buff.readLine();
            System.out.println(str);

            if (Objects.equals(str, "Successful!") || str == null) {
                break;
            }

            pw.println(sc.nextLine());
            pw.flush();
        }

        closeConnections();
    }

    private void closeConnections() throws IOException {
        if (sc != null) {
            sc.close();
        }
        if (s != null) {
            s.close();
        }
        if (in != null) {
            in.close();
        }
        if (pw != null) {
            pw.close();
        }
        if (buff != null) {
            buff.close();
        }
    }
}
