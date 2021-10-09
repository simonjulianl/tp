package gomedic.model.commonfield.exceptions;

/**
 * Signals that the operation is unable to support more items.
 */
public class MaxAddressBookCapacityReached extends RuntimeException {
    public MaxAddressBookCapacityReached() {
        super("The addressbook can only contains max of 999 items of each type at one point of time.");
    }
}
