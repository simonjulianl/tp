package gomedic.model.person.patient;

import static java.util.Objects.requireNonNull;

import gomedic.commons.util.AppUtil;

/**
 * Represents the age of a Patient.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class Age {
    public static final String MESSAGE_CONSTRAINTS =
        "Ages should only contain from 0 to 150";

    /**
     * Age must only contain numeric characters.
     */
    public static final String VALIDATION_REGEX = "[0-9]+";

    public final String age;

    /**
     * Constructs a {@code Age}.
     *
     * @param age Integer in String from 0-150.
     */
    public Age(String age) {
        requireNonNull(age);
        AppUtil.checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        this.age = age;
    }

    /**
     * Returns true if a given age is a valid age from 0 to 150.
     *
     * @param test String number.
     * @return true if valid, else false.
     */
    public static boolean isValidAge(String test) {
        return test.matches(VALIDATION_REGEX) && (Integer.parseInt(test) >= 0 && Integer.parseInt(test) <= 150);
    }

    @Override
    public String toString() {
        return age;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Age// instanceof handles nulls
                && age.equals(((Age) other).age)); // state check
    }

    @Override
    public int hashCode() {
        return age.hashCode();
    }
}
