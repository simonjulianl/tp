package gomedic.model.activity.exceptions;

/**
 * Signals that the operation will result in duplicate Activity (They have the same id).
 */
public class DuplicateActivityFoundException extends RuntimeException {
    public DuplicateActivityFoundException() {
        super("Operation would result in duplicate activity as the existing activity exists already.");
    }
}
