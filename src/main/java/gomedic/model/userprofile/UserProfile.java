package gomedic.model.userprofile;

import java.util.Objects;

import gomedic.commons.util.CollectionUtil;
import gomedic.model.commonfield.Name;
import gomedic.model.person.doctor.Department;

/**
 * Represents the user profile in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class UserProfile {
    // user details
    private final Name name;
    private final Position position;
    private final Department department;
    private final Organization organization;

    /**
     * Every field must be present and not null.
     */
    public UserProfile(Name name, Position position, Department department, Organization organization) {
        CollectionUtil.requireAllNonNull(name, organization, position, department);
        this.name = name;
        this.organization = organization;
        this.position = position;
        this.department = department;
    }

    public Name getName() {
        return name;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Position getPosition() {
        return position;
    }

    public Department getDepartment() {
        return department;
    }

    /**
     * Returns a copy of this UserProfile instance.
     * @return a deep copy of a UserProfile instance.
     */
    public UserProfile copy() {
        return new UserProfile(
                new Name(name.fullName),
                new Position(position.positionName),
                new Department(department.departmentName),
                new Organization(organization.organizationName));
    }

    /**
     * Returns true if both UserProfiles are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UserProfile)) {
            return false;
        }

        UserProfile otherUserProfile = (UserProfile) other;
        return name.equals(otherUserProfile.getName())
                && organization.equals(otherUserProfile.getOrganization())
                && position.equals(otherUserProfile.getPosition())
                && department.equals(otherUserProfile.getDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position, department, organization);
    }

    /**
     * Returns a String representation of a {@code UserProfile}, that includes the name and description of the user.
     *
     * @return a String representation of a {@code UserProfile}.
     */
    @Override
    public String toString() {
        return "Name: " + name + "; Position: " + position
                + "; Department: " + department + "; Organization: " + organization;
    }

    public String getIdentity() {
        return name + ", " + position + ", " + department + ", " + organization;
    }
}
