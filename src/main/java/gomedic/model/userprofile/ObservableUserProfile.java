package gomedic.model.userprofile;

import gomedic.commons.util.CollectionUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

/**
 * An observable of the user profile.
 */
public class ObservableUserProfile {
    private final ObjectProperty<UserProfile> internalUserProfile = new SimpleObjectProperty<>();
    private final ObservableValue<UserProfile> unmodifiableUserProfile = internalUserProfile;

    /**
     * Replaces the user profile with {@code editedUserProfile}.
     */
    public void setUserProfile(UserProfile editedUserProfile) {
        CollectionUtil.requireAllNonNull(editedUserProfile);

        internalUserProfile.setValue(editedUserProfile);
    }

    /**
     * Gets a copy of the current user profile.
     */
    public UserProfile getUserProfile() {
        if (internalUserProfile.getValue() == null) {
            return null;
        }
        return internalUserProfile.getValue().copy();
    }

    /**
     * Returns the backing value as an unmodifiable {@code ObservableValue}.
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
            return internalUserProfile.getValue().equals(otherProfile.internalUserProfile.getValue());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return internalUserProfile.getValue().hashCode();
    }
}
