package gomedic.testutil.modelbuilder;

import gomedic.model.commonfield.Name;
import gomedic.model.person.doctor.Department;
import gomedic.model.userprofile.Organization;
import gomedic.model.userprofile.Position;
import gomedic.model.userprofile.UserProfile;

/**
 * A utility class to help with building UserProfile objects.
 */
public class UserProfileBuilder {
    public static final String DEFAULT_NAME = "John Smith";
    public static final String DEFAULT_POSITION = "Senior Resident";
    public static final String DEFAULT_DEPARTMENT = "Cardiology";
    public static final String DEFAULT_ORGANIZATION = "NUH";

    private Name name;
    private Position position;
    private Department department;
    private Organization organization;

    /**
     * Creates a {@code UserProfileBuilder} with the default details.
     */
    public UserProfileBuilder() {
        name = new Name(DEFAULT_NAME);
        position = new Position(DEFAULT_POSITION);
        department = new Department(DEFAULT_DEPARTMENT);
        organization = new Organization(DEFAULT_ORGANIZATION);
    }

    /**
     * Initializes the UserProfileBuilder with the data of {@code profileToCopy}.
     */
    public UserProfileBuilder(UserProfile profileToCopy) {
        name = profileToCopy.getName();
        position = profileToCopy.getPosition();
        department = profileToCopy.getDepartment();
        organization = profileToCopy.getOrganization();
    }

    /**
     * Sets the {@code Name} of the {@code UserProfile} that we are building.
     */
    public UserProfileBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Position} of the {@code UserProfile} that we are building.
     */
    public UserProfileBuilder withPosition(String position) {
        this.position = new Position(position);
        return this;
    }

    /**
     * Sets the {@code Department} of the {@code UserProfile} that we are building.
     */
    public UserProfileBuilder withDepartment(String department) {
        this.department = new Department(department);
        return this;
    }

    /**
     * Sets the {@code Organization} of the {@code UserProfile} that we are building.
     */
    public UserProfileBuilder withOrganization(String organization) {
        this.organization = new Organization(organization);
        return this;
    }

    public UserProfile build() {
        return new UserProfile(name, position, department, organization);
    }
}
