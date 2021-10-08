package gomedic.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gomedic.commons.exceptions.IllegalValueException;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.DoctorId;
import gomedic.testutil.Assert;
import gomedic.testutil.TypicalPersons;

public class JsonAdaptedDoctorTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ID = "D24";
    private static final String INVALID_DEPARTMENT = " ";

    private static final String VALID_NAME = TypicalPersons.MAIN_DOCTOR.getName().toString();
    private static final String VALID_PHONE = TypicalPersons.MAIN_DOCTOR.getPhone().toString();
    private static final String VALID_ID = TypicalPersons.MAIN_DOCTOR.getId().toString();
    private static final String VALID_DEPARTMENT = TypicalPersons.MAIN_DOCTOR.getDepartment().toString();

    @Test
    public void toModelType_validDoctorDetails_returnsDoctor() throws Exception {
        JsonAdaptedDoctor doctor = new JsonAdaptedDoctor(TypicalPersons.MAIN_DOCTOR);
        Assertions.assertEquals(TypicalPersons.MAIN_DOCTOR, doctor.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDoctor person =
                new JsonAdaptedDoctor(INVALID_NAME, VALID_PHONE, VALID_ID, VALID_DEPARTMENT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDoctor person = new JsonAdaptedDoctor(null, VALID_PHONE, VALID_ID, VALID_DEPARTMENT);
        String expectedMessage = String.format(
                JsonAdaptedDoctor.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedDoctor person =
                new JsonAdaptedDoctor(VALID_NAME, INVALID_PHONE, VALID_ID, VALID_DEPARTMENT);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedDoctor person = new JsonAdaptedDoctor(VALID_NAME, null, VALID_ID, VALID_DEPARTMENT);
        String expectedMessage = String.format(
                JsonAdaptedDoctor.MISSING_FIELD_MESSAGE_FORMAT,
                Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedDoctor person =
                new JsonAdaptedDoctor(VALID_NAME, VALID_PHONE, INVALID_ID, VALID_DEPARTMENT);
        String expectedMessage = DoctorId.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedDoctor person = new JsonAdaptedDoctor(VALID_NAME, VALID_PHONE, null, VALID_DEPARTMENT);
        String expectedMessage = String.format(
                JsonAdaptedDoctor.MISSING_FIELD_MESSAGE_FORMAT,
                DoctorId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDepartment_throwsIllegalValueException() {
        JsonAdaptedDoctor person =
                new JsonAdaptedDoctor(VALID_NAME, VALID_PHONE, VALID_ID, INVALID_DEPARTMENT);
        String expectedMessage = Department.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDepartment_throwsIllegalValueException() {
        JsonAdaptedDoctor person = new JsonAdaptedDoctor(VALID_NAME, VALID_PHONE, VALID_ID, null);
        String expectedMessage = String.format(
                JsonAdaptedDoctor.MISSING_FIELD_MESSAGE_FORMAT,
                Department.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
