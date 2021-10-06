package gomedic.model.activity;

import gomedic.commons.util.AppUtil;
import gomedic.model.commonfield.Id;

/**
 * Represents a general id owned by an activity.
 * Take note however that another class has the same prefix as ActivityId
 * and has the same integer is treated equally with ActivityId.
 * Guarantees: immutable; is valid as declared in {@link #isValidActivityId(Id)})}
 */
public class ActivityId extends Id {
    public static final String MESSAGE_CONSTRAINTS =
            "Id should only contain from 1 to 999, Prefix should be A";
    private static final Character ACTIVITY_PREFIX = 'A';

    /**
     * {@inheritDoc}
     *
     * @param id Integer from 1 to 999
     */
    public ActivityId(Integer id) {
        super(id, ACTIVITY_PREFIX);
        AppUtil.checkArgument(isValidActivityId(this), MESSAGE_CONSTRAINTS);
    }

    /**
     * {@inheritDoc}
     *
     * @param id Integer from 1 to 999
     */
    public ActivityId(String id) {
        super(Integer.parseInt(id.substring(1)), ACTIVITY_PREFIX);
        AppUtil.checkArgument(isValidActivityId(this), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given stringId is a valid activity valid id.
     * Valid if integer is 3 digit, from 1 to 999, prefix is A.
     *
     * @param id Id.
     * @return true if valid, else false.
     */
    public static boolean isValidActivityId(Id id) {
        return isValidActivityId(id.toString());
    }

    /**
     * Returns true if a given stringId is a valid activity valid id.
     * Valid if integer is 3 digit, from 1 to 999, prefix is A.
     *
     * @param aid String.
     * @return true if valid, else false.
     */
    public static boolean isValidActivityId(String aid) {
        if (!isValidIdFormat(aid)) {
            return false;
        }

        int number = Integer.parseInt(aid.substring(1));
        Character prefix = aid.charAt(0);
        boolean isValidPrefix = prefix.equals(ACTIVITY_PREFIX);

        return isValidId(number, prefix) && isValidPrefix;
    }
}
