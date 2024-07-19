package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Optional;
import java.util.Scanner;

public class MessageFinder {
    private static HikariDataSource dataSource;

    public MessageFinder() {
        try {
            HikariConfig config = new HikariConfig("src/main/resources/database.properties");
            dataSource = new HikariDataSource(config);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    public void runFinder() {
        MessagesRepositoryJdbcImpl msgRepository = new MessagesRepositoryJdbcImpl(dataSource);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a message ID");
            String input = sc.nextLine();
            if ("q".equals(input)) {
                break;
            }
            else {
                try {
                    long id = Long.parseLong(input);
                    Optional<Message> message = msgRepository.findById(id);
                    if (message.isPresent()) {
                        System.out.println(message.get());
                    } else {
                        System.out.println("Message with id '" + id + "' not found");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid input");
                }
            }
        }
        sc.close();
        dataSource.close();
    }
}
