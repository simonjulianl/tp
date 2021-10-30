package gomedic.model.person.patient;

import static java.util.Objects.requireNonNull;

import gomedic.commons.util.AppUtil;

/**
 * Represents the height of a Patient in centimetre (cm).
 * Guarantees: immutable; is valid as declared in {@link #isValidHeight(String)}
 */
public class Height {
    public static final String MESSAGE_CONSTRAINTS =
            "Height should be integer between 1 and 300 inclusive";

    /**
     * Height must only contain numeric characters.
     */
    public static final String VALIDATION_REGEX = "[0-9]+";

    public final String height;

    /**
     * Constructs a {@code Height}.
     *
     * @param height Integer in String from 0-300.
     */
    public Height(String height) {
        requireNonNull(height);
        AppUtil.checkArgument(isValidHeight(height), MESSAGE_CONSTRAINTS);
        this.height = height;
    }

    /**
     * Returns true if a given height is a valid height from 1 to 300.
     *
     * @param test String number.
     * @return true if valid, else false.
     */
    public static boolean isValidHeight(String test) {
        try {
            return test.matches(VALIDATION_REGEX) && (Integer.parseInt(test) > 0 && Integer.parseInt(test) <= 300);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return height;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Height// instanceof handles nulls
                && height.equals(((Height) other).height)); // state check
    }

    @Override
    public int hashCode() {
        return height.hashCode();
    }
}
