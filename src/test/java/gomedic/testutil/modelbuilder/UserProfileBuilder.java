package gomedic.testutil.modelbuilder;

import gomedic.model.activity.Description;
import gomedic.model.commonfield.Name;
import gomedic.model.userprofile.UserProfile;

/**
 * A utility class to help with building UserProfile objects.
 */
public class UserProfileBuilder {
    public static final String DEFAULT_NAME = "John Smith";
    public static final String DEFAULT_DESCRIPTION =
            "This is my personal tracker for all work related activities and contacts";

    private Name name;
    private Description description;

    /**
     * Creates a {@code UserProfileBuilder} with the default details.
     */
    public UserProfileBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the UserProfileBuilder with the data of {@code profileToCopy}.
     */
    public UserProfileBuilder(UserProfile profileToCopy) {
        name = profileToCopy.getName();
        description = profileToCopy.getDescription();
    }

    /**
     * Sets the {@code Name} of the {@code UserProfile} that we are building.
     */
    public UserProfileBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code DoctorId} of the {@code Doctor} that we are building.
     */
    public UserProfileBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public UserProfile build() {
        return new UserProfile(name, description);
    }
}
