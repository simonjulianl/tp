package gomedic.storage;

import static gomedic.testutil.TypicalUserProfile.MAIN_PROFILE;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gomedic.commons.exceptions.IllegalValueException;
import gomedic.model.activity.Description;
import gomedic.model.commonfield.Name;
import gomedic.testutil.Assert;

class JsonAdaptedUserProfileTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DESCRIPTION = "aaaa".repeat(500);

    private static final String VALID_NAME = MAIN_PROFILE.getName().toString();
    private static final String VALID_DESCRIPTION = MAIN_PROFILE.getDescription().toString();

    @Test
    public void toModelType_validProfileDetails_returnsUserProfile() throws Exception {
        JsonAdaptedUserProfile userProfile = new JsonAdaptedUserProfile(MAIN_PROFILE);
        Assertions.assertEquals(MAIN_PROFILE, userProfile.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile =
                new JsonAdaptedUserProfile(INVALID_NAME, VALID_DESCRIPTION);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile = new JsonAdaptedUserProfile(null, VALID_DESCRIPTION);
        String expectedMessage = String.format(
                JsonAdaptedUserProfile.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile =
                new JsonAdaptedUserProfile(VALID_NAME, INVALID_DESCRIPTION);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile = new JsonAdaptedUserProfile(VALID_NAME, null);
        String expectedMessage = String.format(
                JsonAdaptedUserProfile.MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }
}
