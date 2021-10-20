package gomedic.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gomedic.commons.exceptions.IllegalValueException;
import gomedic.model.activity.ActivityId;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Time;
import gomedic.testutil.Assert;
import gomedic.testutil.TypicalActivities;

public class JsonAdaptedActivityTest {
    private static final String INVALID_TITLE = "R@chel".repeat(100);
    private static final String INVALID_ID = "C100";
    private static final String INVALID_DESC = "what is this doing".repeat(1000);
    private static final String INVALID_START_TIME = "15/07/2000";
    private static final String INVALID_END_TIME = "15/13/2000 15:00";

    private static final String VALID_TITLE = TypicalActivities.MEETING.getTitle().toString();
    private static final String VALID_START_TIME = TypicalActivities.MEETING.getStartTime().toString();
    private static final String VALID_END_TIME = TypicalActivities.MEETING.getEndTime().toString();
    private static final String VALID_ID = TypicalActivities.MEETING.getActivityId().toString();
    private static final String VALID_DESCRIPTION = TypicalActivities.MEETING.getDescription().toString();
    private static final String VALID_ACTIVITY_PATIENT = null;
    private static final String VALID_APPOINTMENT_PATIENT = TypicalActivities.APPOINTMENT.getPatientId().toString();

    @Test
    public void toModelType_validActivityDetails_returnsDoctor() throws Exception {
        JsonAdaptedActivity activity = new JsonAdaptedActivity(
                VALID_ID,
                VALID_ACTIVITY_PATIENT,
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_START_TIME,
                VALID_END_TIME);
        Assertions.assertEquals(TypicalActivities.MEETING, activity.toModelType());
    }
    @Test
    public void toModelType_validAppointmentDetails_returnsDoctor() throws Exception {
        JsonAdaptedActivity activity = new JsonAdaptedActivity(
                VALID_ID,
                VALID_APPOINTMENT_PATIENT,
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_START_TIME,
                VALID_END_TIME);
        Assertions.assertEquals(TypicalActivities.MEETING, activity.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(VALID_ID,
                        VALID_ACTIVITY_PATIENT,
                        INVALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_START_TIME,
                        VALID_END_TIME);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedActivity activity = new JsonAdaptedActivity(null,
                VALID_ACTIVITY_PATIENT,
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_START_TIME,
                VALID_END_TIME);
        String expectedMessage = String.format(
                JsonAdaptedActivity.MISSING_FIELD_MESSAGE_FORMAT,
                ActivityId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(VALID_ID,
                        VALID_ACTIVITY_PATIENT,
                        VALID_TITLE,
                        INVALID_DESC,
                        VALID_START_TIME,
                        VALID_END_TIME);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedActivity activity = new JsonAdaptedActivity(VALID_ID,
                VALID_ACTIVITY_PATIENT,
                null,
                VALID_DESCRIPTION,
                VALID_START_TIME,
                VALID_END_TIME);
        String expectedMessage = String.format(
                JsonAdaptedActivity.MISSING_FIELD_MESSAGE_FORMAT,
                Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(INVALID_ID,
                        VALID_ACTIVITY_PATIENT,
                        VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_START_TIME,
                        VALID_END_TIME);
        String expectedMessage = ActivityId.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(VALID_ID,
                        VALID_ACTIVITY_PATIENT,
                        VALID_TITLE,
                        VALID_DESCRIPTION,
                        INVALID_START_TIME,
                        VALID_END_TIME);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);

        JsonAdaptedActivity activityTwo =
                new JsonAdaptedActivity(VALID_ID,
                        VALID_ACTIVITY_PATIENT,
                        VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_START_TIME,
                        INVALID_END_TIME);
        String expectedMessageTwo = Time.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessageTwo, activityTwo::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedActivity activity = new JsonAdaptedActivity(VALID_ID,
                VALID_ACTIVITY_PATIENT,
                VALID_TITLE,
                VALID_DESCRIPTION,
                null,
                VALID_END_TIME);
        String expectedMessage = String.format(
                JsonAdaptedActivity.MISSING_FIELD_MESSAGE_FORMAT,
                Time.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);

        JsonAdaptedActivity activityTwo = new JsonAdaptedActivity(VALID_ID,
                VALID_ACTIVITY_PATIENT,
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_START_TIME,
                null);
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activityTwo::toModelType);
    }
}
