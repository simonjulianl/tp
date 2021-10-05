package gomedic.model.person.doctor;

import static java.util.Objects.requireNonNull;

import gomedic.commons.util.AppUtil;

/**
 * Represents the department of a Doctor.
 * Guarantees: immutable; is valid as declared in {@link #isValidDepartmentName(String)}
 */
public class Department {
    public static final String MESSAGE_CONSTRAINTS =
            "Departments should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the department must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String departmentName;

    /**
     * Constructs a {@code Department}.
     *
     * @param departmentName A valid department name.
     */
    public Department(String departmentName) {
        requireNonNull(departmentName);
        AppUtil.checkArgument(isValidDepartmentName(departmentName), MESSAGE_CONSTRAINTS);
        this.departmentName = departmentName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDepartmentName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return departmentName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Department // instanceof handles nulls
                && departmentName.equals(((Department) other).departmentName)); // state check
    }

    @Override
    public int hashCode() {
        return departmentName.hashCode();
    }
}
