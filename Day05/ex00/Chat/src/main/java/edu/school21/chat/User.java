package edu.school21.chat;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private final Long id;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> socializingChatrooms;

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        createdRooms = new ArrayList<>();
        socializingChatrooms = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public List<Chatroom> getSocializingChatrooms() {
        return socializingChatrooms;
    }

    public void setCreatedRooms(List<Chatroom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public void setSocializingChatrooms(List<Chatroom> socializingChatrooms) {
        this.socializingChatrooms = socializingChatrooms;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((!(obj instanceof User)) || this.hashCode() != obj.hashCode()) return false;
        User other = (User) obj;
        return this.id == other.id
                && Objects.equals(this.login, other.login)
                && Objects.equals(this.password, other.password)
                && Objects.equals(this.createdRooms, other.createdRooms)
                && Objects.equals(this.socializingChatrooms, other.socializingChatrooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", login=" + login +
                ", password=" + password +
                '}';
    }
}
