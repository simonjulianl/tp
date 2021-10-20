package gomedic.model.userprofile;

import gomedic.commons.util.CollectionUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

/**
 * A singleton list of the user profile.
 * <p>
 * Supports a minimal set of list operations.
 *
 */
public class ObservableUserProfile {
    private final ObjectProperty<UserProfile> internalUserProfile = new SimpleObjectProperty<>();
    private final ObservableValue<UserProfile> unmodifiableUserProfile = internalUserProfile;

    /**
     * Replaces the user profile in the list with {@code editedUserProfile}.
     */
    public void setUserProfile(UserProfile editedUserProfile) {
        CollectionUtil.requireAllNonNull(editedUserProfile);

        internalUserProfile.setValue(editedUserProfile);
    }

    /**
     * Gets a copy of the current user profile.
     */
    public UserProfile getUserProfile() {
        return internalUserProfile.getValue();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableValue<UserProfile> getUnmodifiableUserProfile() {
        return unmodifiableUserProfile;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof ObservableUserProfile) {
            ObservableUserProfile otherProfile = (ObservableUserProfile) other;
            return internalUserProfile.equals(otherProfile.internalUserProfile);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return internalUserProfile.hashCode();
    }
}
