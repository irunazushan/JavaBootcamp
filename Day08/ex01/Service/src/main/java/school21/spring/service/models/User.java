package school21.spring.service.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class User {
    private Long id;
    private String email;

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((!(obj instanceof User)) || this.hashCode() != obj.hashCode()) return false;
        User other = (User) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.email, other.email);
    }

    @Override
    public String toString() {
        return "{" +
                "id: " + id +
                ", email: " + email +
                '}';
    }

}
