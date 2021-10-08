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

	public final String bloodtype;

	/**
	 * Constructs a {@code BloodType}.
	 *
	 * @param bloodtype String A/B/AB/O.
	 */
	public BloodType(String bloodtype) {
		requireNonNull(bloodtype);
		AppUtil.checkArgument(isValidBloodType(bloodtype), MESSAGE_CONSTRAINTS);
		this.bloodtype = bloodtype;
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
		return bloodtype;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof BloodType // instanceof handles nulls
				&& bloodtype.equals(((BloodType) other).bloodtype)); // state check
	}

	@Override
	public int hashCode() {
		return bloodtype.hashCode();
	}
}
