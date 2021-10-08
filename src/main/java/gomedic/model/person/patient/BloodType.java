package gomedic.model.person.patient;

import static java.util.Objects.requireNonNull;

import gomedic.commons.util.AppUtil;

/**
 * Represents the blood type of a Patient.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodType(String)}
 */
public class BloodType {
    public static final String MESSAGE_CONSTRAINTS =
            "BloodType should only contain A, B, AB, or O, and it should not be blank";

    public final String bloodType;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param bloodType String A/B/AB/O.
     */
    public BloodType(String bloodType) {
        requireNonNull(bloodType);
        AppUtil.checkArgument(isValidBloodType(bloodType), MESSAGE_CONSTRAINTS);
        this.bloodType = bloodType;
    }

    /**
     * Returns true if a given blood type is a valid blood type either A, B, AB, or O.
     *
     * @param test String of the blood type.
     * @return true if valid, else false
     */
    public static boolean isValidBloodType(String test) {
        return test.equals("A") || test.equals("B") || test.equals("AB") || test.equals("O");
    }

    @Override
    public String toString() {
        return bloodType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodType // instanceof handles nulls
                && bloodType.equals(((BloodType) other).bloodType)); // state check
    }

    @Override
    public int hashCode() {
        return bloodType.hashCode();
    }
}
