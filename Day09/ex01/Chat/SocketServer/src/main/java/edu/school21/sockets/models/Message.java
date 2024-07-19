package edu.school21.sockets.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
public class Message {
    private Long id;
    private User sender;
    private String message;
    private LocalDateTime dateTime;

    public Message(Long id, User sender, String message, LocalDateTime dataTime) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.dateTime = dataTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Message)) return false;
        if (this.hashCode() != obj.hashCode()) return false;
        Message other = (Message) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.sender, other.sender)
                && Objects.equals(this.message, other.message)
                && Objects.equals(this.dateTime, other.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, message, dateTime);
    }

    @Override
    public String toString() {
        return "Message : {" +
                "\nid=" + id +
                ",\nsender=" + (sender != null ? sender : "null") +
                ",\nmessage=\"" + message + '"' +
                ",\ndateTime=" + (dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm")) : "null") +
                "\n}";
    }


}
