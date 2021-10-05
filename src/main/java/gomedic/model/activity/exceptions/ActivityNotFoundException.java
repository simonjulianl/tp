package gomedic.model.activity.exceptions;

/**
 * Signals that the operation is unable to find the specified activity.
 */
public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException() {
        super("Activity not found.");
    }

}
