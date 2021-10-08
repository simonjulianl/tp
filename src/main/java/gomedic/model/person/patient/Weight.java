package gomedic.model.person.patient;

import static java.util.Objects.requireNonNull;

import gomedic.commons.util.AppUtil;

/**
 * Represents the weight of a Patient in kilogram (kg).
 * Guarantees: immutable; is valid as declared in {@link #isValidWeight(Integer)}
 */
public class Weight {
    public static final String MESSAGE_CONSTRAINTS =
            "Weight should only contain from 0 to 700";

    /**
     * Weight must only contain numeric characters.
     */
    public static final String VALIDATION_REGEX = "[0-9]+";

    public final String weight;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight Integer from 0-700.
     */
    public Weight(String weight) {
        requireNonNull(weight);
        AppUtil.checkArgument(isValidWeight(weight), MESSAGE_CONSTRAINTS);
        this.weight = weight;
    }

    /**
     * Returns true if a given weight is a valid weight from 0 to 700.
     *
     * @param test Integer in String number.
     * @return true if valid, else false.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(VALIDATION_REGEX) && (Integer.parseInt(test) >= 0 && Integer.parseInt(test) <= 700);
    }

    @Override
    public String toString() {
        return weight;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Weight// instanceof handles nulls
                && weight.equals(((Weight) other).weight)); // state check
    }

    @Override
    public int hashCode() {
        return weight.hashCode();
    }
}
