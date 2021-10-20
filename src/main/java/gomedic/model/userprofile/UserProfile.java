package gomedic.model.userprofile;

import java.util.Objects;

import gomedic.commons.util.CollectionUtil;
import gomedic.model.activity.Description;
import gomedic.model.commonfield.Name;

/**
 * Represents the user profile in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class UserProfile {
    // user details
    private final Name name;
    private final Description description;

    /**
     * Every field must be present and not null.
     */
    public UserProfile(Name name, Description description) {
        CollectionUtil.requireAllNonNull(name, description);
        this.name = name;
        this.description = description;
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns a copy of this UserProfile instance.
     * @return a deep copy of a UserProfile instance.
     */
    public UserProfile copy() {
        return new UserProfile(new Name(name.fullName), new Description(description.toString()));
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
                && description.equals(otherUserProfile.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    /**
     * Returns a String representation of a {@code UserProfile}, that includes the name and description of the user.
     *
     * @return a String representation of a {@code UserProfile}.
     */
    @Override
    public String toString() {
        return "Name: " + name + "; Description: " + description;
    }
}
