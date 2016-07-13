package co.redeye.spring.challenge.services;

/**
 * Exception to be used for problems with authentication.
 * The message will be delivered to the user.
 */
public class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }
}
