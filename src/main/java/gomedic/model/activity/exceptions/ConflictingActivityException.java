package gomedic.model.activity.exceptions;

/**
 * Signals that the operation results in conflicting activity.
 */
public class ConflictingActivityException extends RuntimeException {
    public ConflictingActivityException() {
        super("There is partial/full overlap in the activity timing");
    }
}
