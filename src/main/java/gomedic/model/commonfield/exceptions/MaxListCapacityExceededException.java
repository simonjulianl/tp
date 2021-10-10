package gomedic.model.commonfield.exceptions;

public class MaxListCapacityExceededException extends RuntimeException {
    public MaxListCapacityExceededException() {
        super("The addressbook can only contains max of 999 items of each type at one point of time.");
    }
}
