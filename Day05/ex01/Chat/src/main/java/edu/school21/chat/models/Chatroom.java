package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chatroom {
    private final Long id;
    private String name;
    private User owner;
    private List<Message> messages;

    public Chatroom(Long id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        messages = new ArrayList<Message>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Chatroom)) return false;
        if (this.hashCode() != obj.hashCode()) return false;
        Chatroom other = (Chatroom) obj;
        if (this.id == other.id
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.owner, other.owner)
                && Objects.equals(this.messages,other.messages)
        ) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name=\"" + name + '"' +
                ", creator=" + (owner != null ? '"' + owner.getLogin() + '"' : "null") +
                ", messages=" + (!messages.isEmpty() ? messages : "null") +
                '}';
    }
}
