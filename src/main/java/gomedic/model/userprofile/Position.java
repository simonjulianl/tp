package gomedic.model.userprofile;

import static java.util.Objects.requireNonNull;

import gomedic.commons.util.AppUtil;

/**
 * Represents the position.
 * Guarantees: immutable; is valid as declared in {@link #isValidPositionName(String)}
 */
public class Position {
    public static final String MESSAGE_CONSTRAINTS =
            "Positions should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the position must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String positionName;

    /**
     * Constructs a {@code Position}.
     *
     * @param positionName A valid position name.
     */
    public Position(String positionName) {
        requireNonNull(positionName);
        AppUtil.checkArgument(isValidPositionName(positionName), MESSAGE_CONSTRAINTS);
        this.positionName = positionName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPositionName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return positionName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Position // instanceof handles nulls
                && positionName.equals(((Position) other).positionName)); // state check
    }

    @Override
    public int hashCode() {
        return positionName.hashCode();
    }
}
