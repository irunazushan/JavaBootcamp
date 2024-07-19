package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket ss;
    private final Socket s ;
    private final PrintWriter pw;
    private final InputStreamReader in;
    private final BufferedReader buff;

    public Server(int port) throws IOException {
        ss = new ServerSocket(port);
        s = ss.accept();
        System.out.println("Client connected!");
        in = new InputStreamReader(s.getInputStream());
        pw = new PrintWriter(s.getOutputStream());
        buff = new BufferedReader(in);
    }

    public  void runServer () throws IOException {

        pw.println("Hello from Server!");
        pw.flush();
        String str = buff.readLine();

        if (str.equals("signUp")) {
            pw.println("Enter username:");
            pw.flush();
            String name = buff.readLine();

            pw.println("Enter password:");
            pw.flush();
            String password = buff.readLine();

            ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
            UsersService us = context.getBean("usersServiceImpl", UsersService.class);
            us.signUp(name, password);

            System.out.println("Client with name: " + name + " is added to DB");
            pw.println("Successful!");
            pw.flush();
        }
        closeConnections();
    }

    private void closeConnections() throws IOException {
        if (ss != null) {
            ss.close();
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
