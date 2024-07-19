package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;

import java.io.IOException;

public class Main {
    private static final String SERVER_PORT_PREFIX = "--server-port=";
    private static final int INVALID_PORT = -1;

    public static void main(String[] args) {
        int port = getPort(args);
        if (port == INVALID_PORT) {
            throw new RuntimeException("Invalid arguments are given");
        }

        try {
            Client client = new Client(port);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getPort(String[] args) {
        int port = INVALID_PORT;
        if (args.length == 1 && args[0].startsWith(SERVER_PORT_PREFIX)) {
            port = Integer.parseInt(args[0].substring(SERVER_PORT_PREFIX.length()));
        }
        return port;
    }
}
