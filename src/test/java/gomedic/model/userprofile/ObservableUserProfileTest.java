package gomedic.model.userprofile;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalUserProfile.MAIN_PROFILE;
import static gomedic.testutil.TypicalUserProfile.OTHER_PROFILE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ObservableUserProfileTest {

    private final ObservableUserProfile observableUserProfile = new ObservableUserProfile();

    @Test
    public void constructor_nullConstructor_isEmpty() {
        assertNull(observableUserProfile.getUserProfile());
    }

    @Test
    public void setUserProfile_nullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> observableUserProfile.setUserProfile(null));
    }

    @Test
    public void setUserProfile_validValue_success() {
        assertDoesNotThrow(() -> observableUserProfile.setUserProfile(MAIN_PROFILE));
    }

    @Test
    public void getUserProfile_validValue_returnsCorrectValue() {
        assertDoesNotThrow(() -> observableUserProfile.setUserProfile(MAIN_PROFILE));
        assertEquals(observableUserProfile.getUserProfile(), MAIN_PROFILE);
    }

    @Test
    public void getUnmodifiableUserProfile_validValue_returnsCorrectValue() {
        assertDoesNotThrow(() -> observableUserProfile.setUserProfile(MAIN_PROFILE));
        assertEquals(observableUserProfile.getUnmodifiableUserProfile().getValue(), MAIN_PROFILE);
    }

    @Test
    void testEquals() {
        observableUserProfile.setUserProfile(MAIN_PROFILE);

        ObservableUserProfile sameContents = new ObservableUserProfile();
        sameContents.setUserProfile(MAIN_PROFILE);

        ObservableUserProfile diffContents = new ObservableUserProfile();
        diffContents.setUserProfile(OTHER_PROFILE);

        assertNotEquals(observableUserProfile, diffContents);
        assertEquals(observableUserProfile, observableUserProfile);
        assertEquals(observableUserProfile, sameContents);
    }
}
