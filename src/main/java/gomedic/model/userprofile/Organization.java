package gomedic.model.userprofile;

import static java.util.Objects.requireNonNull;

import gomedic.commons.util.AppUtil;

/**
 * Represents the organization.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrganizationName(String)}
 */
public class Organization {
    public static final String MESSAGE_CONSTRAINTS =
            "Organizations should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the organization must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String organizationName;

    /**
     * Constructs a {@code Organization}.
     *
     * @param organizationName A valid organization name.
     */
    public Organization(String organizationName) {
        requireNonNull(organizationName);
        AppUtil.checkArgument(isValidOrganizationName(organizationName), MESSAGE_CONSTRAINTS);
        this.organizationName = organizationName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidOrganizationName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return organizationName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Organization // instanceof handles nulls
                && organizationName.equals(((Organization) other).organizationName)); // state check
    }

    @Override
    public int hashCode() {
        return organizationName.hashCode();
    }
}
