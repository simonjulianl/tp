package gomedic.model.person.patient;

import static java.util.Objects.requireNonNull;

import gomedic.commons.util.AppUtil;

/**
 * Represents the age of a Patient.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(Integer)}
 */
public class Age {
    public static final String MESSAGE_CONSTRAINTS =
        "Ages should only contain from 0 to 150";

    public final Integer age;

    /**
     * Constructs a {@code Age}.
     *
     * @param age Integer from 0-150.
     */
    public Age(Integer age) {
        requireNonNull(age);
        AppUtil.checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        this.age = age;
    }

    /**
     * Returns true if a given age is a valid age from 0 to 150.
     *
     * @param age Integer number.
     * @return true if valid, else false.
     */
    public static boolean isValidAge(Integer age) {
        return age >= 0 && age <= 150;
    }

    @Override
    public String toString() {
        return age.toString();
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
