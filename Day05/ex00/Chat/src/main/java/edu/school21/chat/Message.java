package edu.school21.chat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private final Long id;
    private final User author;
    private final Chatroom room;
    private final String text;
    private final String data;
    private final String time;

    public Message(Long id, User author, Chatroom room, String text) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        LocalDateTime now = LocalDateTime.now();
        this.data = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public String getData() {
        return data;
    }

    public String getTime() {
        return time;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Message)) return false;
        if (this.hashCode() != obj.hashCode()) return false;
        Message other = (Message) obj;
        if (this.id == other.id
                && Objects.equals(this.author, other.author)
                && Objects.equals(this.room, other.room)
                && Objects.equals(this.text,other.text)
                && Objects.equals(this.data,other.data)
                && Objects.equals(this.time,other.time)
        ) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, data, time);
    }

    @Override
    public String toString() {
        return "Message {" +
                "id=" + id +
                ", author=" + (author != null ? author.getLogin() : "null") +
                ", room=" + (room != null? room.getName() : "null") +
                ", text=" + text +
                ", data time=" + data + ' ' + time +
                '}';
    }
}
