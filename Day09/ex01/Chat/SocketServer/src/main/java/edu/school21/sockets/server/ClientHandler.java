package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private static List<ClientHandler> clients = new ArrayList<>();
    private Socket s;
    private PrintWriter pw;
    private InputStreamReader in;
    private BufferedReader buff;

    ClientHandler(Socket socket) {
        try {
            s = socket;
            clients.add(this);
            in = new InputStreamReader(s.getInputStream());
            pw = new PrintWriter(s.getOutputStream());
            buff = new BufferedReader(in);
        } catch (IOException e) {
            closeConnections();
        }
    }

    @Override
    public void run() {
        pw.println("Hello from Server!");
        pw.flush();
        while (s.isConnected()) {
            try {
                runClientHandler();
            } catch (IOException e) {
                closeConnections();
                break;
            }
        }
    }

    private void runClientHandler() throws IOException {
        String str = buff.readLine();

        if (str.equals("signUp")) {
            signUpUser();
        } else if (str.equals("signIn")) {
            signInUser();
        }
    }

    private void signUpUser() throws IOException {
        pw.println("Enter username:");
        pw.flush();
        String name = buff.readLine();

        pw.println("Enter password:");
        pw.flush();
        String password = buff.readLine();

        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        UsersService us = context.getBean("usersServiceImpl", UsersService.class);
        us.signUp(name, password);

        System.out.println("User with name: " + name + " is added to DB");
        pw.println("Successful!");
        pw.flush();
    }

    private void signInUser() throws IOException {
        pw.println("Enter username:");
        pw.flush();
        String name = buff.readLine();

        pw.println("Enter password:");
        pw.flush();
        String password = buff.readLine();

        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        UsersService us = context.getBean("usersServiceImpl", UsersService.class);
        User user = us.signIn(name, password);
        if (user != null) {
            broadcastMessage(user.getName() + " connected to chat!");
            pw.println("Start messaging");
            pw.flush();
            String message = buff.readLine();
            while (!message.equals("Exit")) {
                us.sendMessage(user, message);
                broadcastMessage(user.getName() + ": " + message);
                message = buff.readLine();
            }
            broadcastMessage(user.getName() + " left from chat!");
            pw.println("You have left the chat.");
            pw.flush();
            closeConnections();
        } else {
            pw.println("Incorrect login or password");
            pw.flush();
        }
    }

    private void broadcastMessage(String message) {
        for (ClientHandler ch : clients) {
            if (ch != this) {
                ch.pw.println(message);
                ch.pw.flush();
            }
        }
    }

    private void closeConnections() {
        clients.remove(this);
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
