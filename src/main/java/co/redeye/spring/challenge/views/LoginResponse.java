package co.redeye.spring.challenge.views;

/**
 * Standard response object for login/registering
 */
public class LoginResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
