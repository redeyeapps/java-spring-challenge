package co.redeye.spring.challenge.db;

import javax.persistence.*;
import java.util.List;

/**
 * Persistence ORM for registered users.
 * <p>
 * This is my first time using automatically generated data definition.
 */
@Entity
@Table(name = "users",
        indexes = {@Index(name = "username_index", columnList = "username", unique = true),
                @Index(name = "token_index", columnList = "token", unique = true)})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false, unique = true)
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}