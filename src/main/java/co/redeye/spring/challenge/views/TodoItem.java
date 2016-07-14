package co.redeye.spring.challenge.views;

import co.redeye.spring.challenge.Utils;
import co.redeye.spring.challenge.exceptions.InvalidItemException;

/**
 * Represents a single to do list item.
 */
public class TodoItem {
    Boolean done;
    String text;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void validate() throws InvalidItemException {
        if (!Utils.stringPresent(text)) {
            throw new InvalidItemException("Missing field 'text'");
        }
        if (done == null) {
            throw new InvalidItemException("Missing field 'done' (true|false)");
        }
    }
}
