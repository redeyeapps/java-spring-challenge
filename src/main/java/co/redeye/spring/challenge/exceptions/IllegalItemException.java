package co.redeye.spring.challenge.exceptions;

/**
 * Exception class for users attempting to manipulate to do list items which they do not own.
 * Results in a Forbidden Status.
 */
public class IllegalItemException extends UserException {
    public IllegalItemException(String message) {
        super(message);
    }
}
