package gomedic.model.commonfield;

import gomedic.commons.util.AppUtil;
import gomedic.commons.util.CollectionUtil;

/**
 * Represents a general id owned by a model.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(Integer, Character)}
 */
public abstract class Id {
    public static final String MESSAGE_CONSTRAINTS =
            "Id should only contain from 1 to 999, Prefix should be A-Z (capitalized)";
    private final String value;

    /**
     * Constructs a {@code Id}.
     *
     * @param id Integer from 0-999.
     * @param prefix Char from A-Z only.
     */
    public Id(Integer id, Character prefix) {
        CollectionUtil.requireAllNonNull(id, prefix);
        AppUtil.checkArgument(isValidId(id, prefix), MESSAGE_CONSTRAINTS);
        int digit = 3;
        value = String.format(prefix + "%0" + digit + "d", id);
    }

    /**
     * Returns true if a given stringId is a valid id.
     * Valid if integer is 3 digit, from 1 to 999, prefix is A - Z.
     *
     * @param id Integer number.
     * @param prefix Character prefix.
     * @return true if valid, else false.
     */
    public static boolean isValidId(Integer id, Character prefix) {
        boolean isValidNumber = id > 0 && id <= 999;
        boolean isValidPrefix = prefix >= 'A' && prefix <= 'Z';

        return isValidNumber && isValidPrefix;
    }

    /**
     * Returns true id is made of 1 upper case alphabet followed by 3 digits.
     *
     * @param id String
     * @return true if valid, else false.
     */
    public static boolean isValidIdFormat(String id) {
        boolean isValidNumber = isNumeric(id.substring(1)) && id.substring(1).length() == 3;
        char prefix = id.charAt(0);
        boolean isValidPrefix = 'A' <= prefix && prefix <= 'Z';

        return isValidNumber && isValidPrefix;
    }

    private static boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit
                || (other instanceof Id // instanceof handles nulls and wrong types
                && value.equals(((Id) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
