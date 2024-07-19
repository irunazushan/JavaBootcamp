package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime dateTime;

    public Message(Long id, User author, Chatroom room, String text, LocalDateTime dataTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.dateTime = dataTime;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDataTime() { return dateTime; }

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
                && Objects.equals(this.dateTime,other.dateTime)
        ) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, dateTime);
    }

    @Override
    public String toString() {
        return "Message : {" +
                "\nid=" + id +
                ",\nauthor=" + (author != null ? author : "null") +
                ",\nroom=" + (room != null? room : "null") +
                ",\ntext=\"" + text + '"' +
                ",\ndateTime=" + (dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm")) : "null") +
                "\n}";
    }
}
