package co.redeye.spring.challenge.exceptions;

/**
 * For when a user submits an invalid to do list item.
 */
public class InvalidItemException extends UserException {
    public InvalidItemException(String message) {
        super(message);
    }
}
