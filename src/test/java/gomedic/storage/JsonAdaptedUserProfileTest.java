package gomedic.storage;

import static gomedic.testutil.TypicalUserProfile.MAIN_PROFILE;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gomedic.commons.exceptions.IllegalValueException;
import gomedic.model.commonfield.Name;
import gomedic.model.person.doctor.Department;
import gomedic.model.userprofile.Organization;
import gomedic.model.userprofile.Position;
import gomedic.testutil.Assert;

class JsonAdaptedUserProfileTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_POSITION = "R@chel";
    private static final String INVALID_DEPARTMENT = "R@chel";
    private static final String INVALID_ORGANIZATION = "R@chel";

    private static final String VALID_NAME = MAIN_PROFILE.getName().toString();
    private static final String VALID_POSITION = MAIN_PROFILE.getPosition().toString();
    private static final String VALID_DEPARTMENT = MAIN_PROFILE.getDepartment().toString();
    private static final String VALID_ORGANIZATION = MAIN_PROFILE.getOrganization().toString();

    @Test
    public void toModelType_validProfileDetails_returnsUserProfile() throws Exception {
        JsonAdaptedUserProfile userProfile = new JsonAdaptedUserProfile(MAIN_PROFILE);
        Assertions.assertEquals(MAIN_PROFILE, userProfile.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile =
                new JsonAdaptedUserProfile(INVALID_NAME, VALID_POSITION, VALID_DEPARTMENT, VALID_ORGANIZATION);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile =
                new JsonAdaptedUserProfile(null, VALID_POSITION, VALID_DEPARTMENT, VALID_ORGANIZATION);
        String expectedMessage = String.format(
                JsonAdaptedUserProfile.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }

    @Test
    public void toModelType_invalidPosition_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile =
                new JsonAdaptedUserProfile(VALID_NAME, INVALID_POSITION, VALID_DEPARTMENT, VALID_ORGANIZATION);
        String expectedMessage = Position.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }

    @Test
    public void toModelType_nullPosition_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile =
                new JsonAdaptedUserProfile(VALID_NAME, null, VALID_DEPARTMENT, VALID_ORGANIZATION);
        String expectedMessage = String.format(
                JsonAdaptedUserProfile.MISSING_FIELD_MESSAGE_FORMAT,
                Position.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }

    @Test
    public void toModelType_invalidDepartment_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile =
                new JsonAdaptedUserProfile(VALID_NAME, VALID_POSITION, INVALID_DEPARTMENT, VALID_ORGANIZATION);
        String expectedMessage = Department.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }

    @Test
    public void toModelType_nullDepartment_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile =
                new JsonAdaptedUserProfile(VALID_NAME, VALID_POSITION, null, VALID_ORGANIZATION);
        String expectedMessage = String.format(
                JsonAdaptedUserProfile.MISSING_FIELD_MESSAGE_FORMAT,
                Department.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }

    @Test
    public void toModelType_invalidOrganization_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile =
                new JsonAdaptedUserProfile(VALID_NAME, VALID_POSITION, VALID_DEPARTMENT, INVALID_ORGANIZATION);
        String expectedMessage = Organization.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }

    @Test
    public void toModelType_nullOrganization_throwsIllegalValueException() {
        JsonAdaptedUserProfile userProfile =
                new JsonAdaptedUserProfile(VALID_NAME, VALID_POSITION, VALID_DEPARTMENT, null);
        String expectedMessage = String.format(
                JsonAdaptedUserProfile.MISSING_FIELD_MESSAGE_FORMAT,
                Organization.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userProfile::toModelType);
    }
}
