package co.redeye.spring.challenge.exceptions;

/**
 * Exception to be used for problems with authentication.
 * The message will be delivered to the user.
 */
public class AuthenticationException extends UserException {
    public AuthenticationException(String message) {
        super(message);
    }
}
