package co.redeye.spring.challenge.views;

import co.redeye.spring.challenge.Utils;
import co.redeye.spring.challenge.exceptions.AuthenticationException;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a request to register/login
 */
public class LoginRequest {
    private String username;
    private String password;

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

    public void validate() throws AuthenticationException {
        if (!Utils.stringPresent(username)) {
            throw new AuthenticationException("Invalid username.");
        }
        if (!Utils.stringPresent(password)) {
            throw new AuthenticationException("Invalid password.");
        }
    }
}
