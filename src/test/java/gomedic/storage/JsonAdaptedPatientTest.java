package gomedic.storage;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gomedic.commons.exceptions.IllegalValueException;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.patient.Age;
import gomedic.model.person.patient.BloodType;
import gomedic.model.person.patient.Gender;
import gomedic.model.person.patient.Height;
import gomedic.model.person.patient.PatientId;
import gomedic.model.person.patient.Weight;
import gomedic.testutil.Assert;
import gomedic.testutil.TypicalPersons;

public class JsonAdaptedPatientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ID = "D24";
    private static final String INVALID_AGE = " ";
    private static final String INVALID_BLOODTYPE = " ";
    private static final String INVALID_GENDER = " ";
    private static final String INVALID_HEIGHT = " ";
    private static final String INVALID_WEIGHT = " ";
    private static final List<JsonAdaptedTag> INVALID_MEDICALCONDITIONS = null;

    private static final String VALID_NAME = TypicalPersons.MAIN_PATIENT.getName().toString();
    private static final String VALID_PHONE = TypicalPersons.MAIN_PATIENT.getPhone().toString();
    private static final String VALID_ID = TypicalPersons.MAIN_PATIENT.getId().toString();
    private static final String VALID_AGE = TypicalPersons.MAIN_PATIENT.getAge().toString();
    private static final String VALID_BLOODTYPE = TypicalPersons.MAIN_PATIENT.getBloodType().toString();
    private static final String VALID_GENDER = TypicalPersons.MAIN_PATIENT.getGender().toString();
    private static final String VALID_HEIGHT = TypicalPersons.MAIN_PATIENT.getHeight().toString();
    private static final String VALID_WEIGHT = TypicalPersons.MAIN_PATIENT.getWeight().toString();
    private static final List<JsonAdaptedTag> VALID_MEDICALCONDITIONS = TypicalPersons.MAIN_PATIENT
        .getMedicalConditions().stream()
        .map(JsonAdaptedTag::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validPatientDetails_returnsPatient() throws Exception {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(TypicalPersons.MAIN_PATIENT);
        Assertions.assertEquals(TypicalPersons.MAIN_PATIENT, patient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(INVALID_NAME, VALID_PHONE, VALID_ID, VALID_AGE, VALID_BLOODTYPE,
                VALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPatient person = new JsonAdaptedPatient(null, VALID_PHONE, VALID_ID, VALID_AGE, VALID_BLOODTYPE,
            VALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = String.format(
            JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
            Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, INVALID_PHONE, VALID_ID, VALID_AGE, VALID_BLOODTYPE,
                VALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPatient person = new JsonAdaptedPatient(VALID_NAME, null, VALID_ID, VALID_AGE, VALID_BLOODTYPE,
            VALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = String.format(
            JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
            Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, INVALID_ID, VALID_AGE, VALID_BLOODTYPE,
                VALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = PatientId.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, null, VALID_AGE, VALID_BLOODTYPE,
                VALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = String.format(
            JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
            PatientId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_ID, INVALID_AGE, VALID_BLOODTYPE,
                VALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = Age.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_ID, null, VALID_BLOODTYPE,
                VALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = String.format(
            JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
            Age.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidBloodType_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_ID, VALID_AGE, INVALID_BLOODTYPE,
                VALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = BloodType.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullBloodType_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_ID, VALID_AGE, null,
                VALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = String.format(
            JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
            BloodType.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_ID, VALID_AGE, VALID_BLOODTYPE,
                INVALID_GENDER, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_ID, VALID_AGE, VALID_BLOODTYPE,
                null, VALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = String.format(
            JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
            Gender.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidHeight_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_ID, VALID_AGE, VALID_BLOODTYPE,
                VALID_GENDER, INVALID_HEIGHT, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = Height.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullHeight_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_ID, VALID_AGE, VALID_BLOODTYPE,
                VALID_GENDER, null, VALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = String.format(
            JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
            Height.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidWeight_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_ID, VALID_AGE, VALID_BLOODTYPE,
                VALID_GENDER, VALID_HEIGHT, INVALID_WEIGHT, VALID_MEDICALCONDITIONS);
        String expectedMessage = Weight.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullWeight_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_NAME, VALID_PHONE, VALID_ID, VALID_AGE, VALID_BLOODTYPE,
                VALID_GENDER, VALID_HEIGHT, null, VALID_MEDICALCONDITIONS);
        String expectedMessage = String.format(
            JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT,
            Weight.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
