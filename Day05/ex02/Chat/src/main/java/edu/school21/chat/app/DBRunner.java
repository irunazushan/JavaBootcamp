package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exception.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class DBRunner {
    private static HikariDataSource dataSource;

    public DBRunner() {
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

    public void insertMessage(Long userId, Long roomId, String text ) {
        MessagesRepositoryJdbcImpl msgRepository = new MessagesRepositoryJdbcImpl(dataSource);
        User author = msgRepository.findUserById(userId);
        Chatroom room = msgRepository.findChatroomById(roomId);
        if (author == null || room == null) {
            throw new NotSavedSubEntityException();
        }
        Message message = new Message(null, author, room, text, LocalDateTime.now());
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
        messagesRepository.save(message);
        System.out.println(message.getId());
    }
}
