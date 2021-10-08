package gomedic.model.person.patient;

import static java.util.Objects.requireNonNull;

import gomedic.commons.util.AppUtil;

/**
 * Represents the height of a Patient in centimetre (cm).
 * Guarantees: immutable; is valid as declared in {@link #isValidHeight(Integer)}
 */
public class Height {
	public static final String MESSAGE_CONSTRAINTS =
			"Height should only contain from 0 to 300";

	public final Integer height;

	/**
	 * Constructs a {@code Height}.
	 *
	 * @param height Integer from 0-300.
	 */
	public Height(Integer height) {
		requireNonNull(height);
		AppUtil.checkArgument(isValidHeight(height), MESSAGE_CONSTRAINTS);
		this.height = height;
	}

	/**
	 * Returns true if a given height is a valid height from 0 to 300.
	 *
	 * @param height Integer number.
	 * @return true if valid, else false.
	 */
	public static boolean isValidHeight(Integer height) {
		return height >= 0 && height <= 300;
	}

	@Override
	public String toString() {
		return height.toString();
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
