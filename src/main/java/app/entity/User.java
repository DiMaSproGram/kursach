package app.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "my_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private int password;

    public User() {
    }
    public User(String username, int password) {
        this.username = username;
        this.password = password;
    }
}
