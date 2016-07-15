package co.redeye.spring.challenge.views;

/**
 * Standard response for when an error has occurred.
 */
public class ErrorResponse {
    String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
