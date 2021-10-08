package gomedic.model.person.patient;

import static java.util.Objects.requireNonNull;

import gomedic.commons.util.AppUtil;

/**
 * Represents the gender of a Patient.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {
    public static final String MESSAGE_CONSTRAINTS =
            "Gender should only contain M for Male, F for Female, or O for Others, and it should not be blank";

    /**
     * Gender must only be either M, F, or O.
     */
    public static final String VALIDATION_REGEX = "[MFO]";

    public final String gender;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender Character M/F/O.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        AppUtil.checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        this.gender = gender;
    }

    /**
     * Returns true if a given gender is a valid gender either M, F, or O.
     *
     * @param test String of the gender.
     * @return true if valid, else false
     */
    public static boolean isValidGender(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return gender;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && gender.equals(((Gender) other).gender)); // state check
    }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }
}
