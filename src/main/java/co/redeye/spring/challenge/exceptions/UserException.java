package co.redeye.spring.challenge.exceptions;

/**
 * Base checked exception class for all exceptions caused by bad user input.
 * The message attached to this must be appropriate to send to the end user.
 */
public class UserException extends Exception {
    public UserException(String message) {
        super(message);
    }
}
