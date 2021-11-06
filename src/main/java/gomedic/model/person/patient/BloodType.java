package gomedic.model.person.patient;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import gomedic.commons.util.AppUtil;

/**
 * Represents the blood type of a Patient.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodType(String)}
 */
public class BloodType {
    public static final String MESSAGE_CONSTRAINTS =
            "Blood type should only contain A+, A-, B+, B-, AB+, AB-, O+, or O-, and it should not be blank. All non"
                + " capital letters will be capitalized";

    public final String bloodType;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param bloodType String A+/A-/B+/B-/AB+/AB-/O+/O-.
     */
    public BloodType(String bloodType) {
        requireNonNull(bloodType);
        AppUtil.checkArgument(isValidBloodType(bloodType), MESSAGE_CONSTRAINTS);
        this.bloodType = bloodType.toUpperCase();
    }

    /**
     * Returns true if a given blood type is a valid blood type either A+, A-, B+, B-, AB+, AB-, O+, or O-.
     *
     * @param test String of the blood type.
     * @return true if valid, else false
     */
    public static boolean isValidBloodType(String test) {
        String[] values = {"A+", "A-", "a+", "a-", "B+", "B-", "b+", "b-", "O+", "O-", "o+", "o-",
                "AB+", "AB-", "ab+", "ab-", "Ab+", "Ab-", "aB+", "aB-"};
        return Arrays.stream(values).anyMatch(test::equals);
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
