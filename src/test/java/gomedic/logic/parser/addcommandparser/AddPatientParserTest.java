package gomedic.logic.parser.addcommandparser;

import static gomedic.testutil.TypicalPersons.MAIN_PATIENT;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.addcommand.AddPatientCommand;
import gomedic.logic.parser.CommandParserTestUtil;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.patient.Age;
import gomedic.model.person.patient.BloodType;
import gomedic.model.person.patient.Gender;
import gomedic.model.person.patient.Height;
import gomedic.model.person.patient.Patient;
import gomedic.model.person.patient.Weight;

public class AddPatientParserTest {
    private final AddPatientCommandParser parser = new AddPatientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Patient expectedPatient = MAIN_PATIENT;

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser,
            CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, new AddPatientCommand(
                expectedPatient.getName(),
                expectedPatient.getPhone(),
                expectedPatient.getAge(),
                expectedPatient.getBloodType(),
                expectedPatient.getGender(),
                expectedPatient.getHeight(),
                expectedPatient.getWeight(),
                expectedPatient.getMedicalConditions()));

        // multiple name - last name will be accepted
        CommandParserTestUtil.assertParseSuccess(parser,
            CommandTestUtil.VALID_DESC_NAME_OTHER_PATIENT
                + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, new AddPatientCommand(
                expectedPatient.getName(),
                expectedPatient.getPhone(),
                expectedPatient.getAge(),
                expectedPatient.getBloodType(),
                expectedPatient.getGender(),
                expectedPatient.getHeight(),
                expectedPatient.getWeight(),
                expectedPatient.getMedicalConditions()));

        // multiple phones - last phone accepted
        CommandParserTestUtil.assertParseSuccess(parser,
            CommandTestUtil.VALID_DESC_PHONE_OTHER_PATIENT
                + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, new AddPatientCommand(
                expectedPatient.getName(),
                expectedPatient.getPhone(),
                expectedPatient.getAge(),
                expectedPatient.getBloodType(),
                expectedPatient.getGender(),
                expectedPatient.getHeight(),
                expectedPatient.getWeight(),
                expectedPatient.getMedicalConditions()));

        // multiple age, last age accepted
        CommandParserTestUtil.assertParseSuccess(parser,
            CommandTestUtil.VALID_DESC_AGE_OTHER_PATIENT
                + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, new AddPatientCommand(
                expectedPatient.getName(),
                expectedPatient.getPhone(),
                expectedPatient.getAge(),
                expectedPatient.getBloodType(),
                expectedPatient.getGender(),
                expectedPatient.getHeight(),
                expectedPatient.getWeight(),
                expectedPatient.getMedicalConditions()));

        // multiple blood type, last blood type accepted
        CommandParserTestUtil.assertParseSuccess(parser,
            CommandTestUtil.VALID_DESC_BLOODTYPE_OTHER_PATIENT
                + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, new AddPatientCommand(
                expectedPatient.getName(),
                expectedPatient.getPhone(),
                expectedPatient.getAge(),
                expectedPatient.getBloodType(),
                expectedPatient.getGender(),
                expectedPatient.getHeight(),
                expectedPatient.getWeight(),
                expectedPatient.getMedicalConditions()));

        // multiple gender, last gender accepted
        CommandParserTestUtil.assertParseSuccess(parser,
            CommandTestUtil.VALID_DESC_GENDER_OTHER_PATIENT
                + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, new AddPatientCommand(
                expectedPatient.getName(),
                expectedPatient.getPhone(),
                expectedPatient.getAge(),
                expectedPatient.getBloodType(),
                expectedPatient.getGender(),
                expectedPatient.getHeight(),
                expectedPatient.getWeight(),
                expectedPatient.getMedicalConditions()));

        // multiple height, last height accepted
        CommandParserTestUtil.assertParseSuccess(parser,
            CommandTestUtil.VALID_DESC_HEIGHT_OTHER_PATIENT
                + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, new AddPatientCommand(
                expectedPatient.getName(),
                expectedPatient.getPhone(),
                expectedPatient.getAge(),
                expectedPatient.getBloodType(),
                expectedPatient.getGender(),
                expectedPatient.getHeight(),
                expectedPatient.getWeight(),
                expectedPatient.getMedicalConditions()));

        // multiple weight, last weight accepted
        CommandParserTestUtil.assertParseSuccess(parser,
            CommandTestUtil.VALID_DESC_WEIGHT_OTHER_PATIENT
                + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, new AddPatientCommand(
                expectedPatient.getName(),
                expectedPatient.getPhone(),
                expectedPatient.getAge(),
                expectedPatient.getBloodType(),
                expectedPatient.getGender(),
                expectedPatient.getHeight(),
                expectedPatient.getWeight(),
                expectedPatient.getMedicalConditions()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(
            Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            AddPatientCommand.MESSAGE_USAGE);

        // missing name
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT,
            expectedMessage);

        // missing phone
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT,
            expectedMessage);

        // missing age
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT,
            expectedMessage);

        // missing blood type
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT,
            expectedMessage);

        // missing gender
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT,
            expectedMessage);

        // missing height
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT,
            expectedMessage);

        // missing weight
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.INVALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.INVALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, Phone.MESSAGE_CONSTRAINTS);

        // invalid age
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.INVALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, Age.MESSAGE_CONSTRAINTS);

        // invalid blood type
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.INVALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, BloodType.MESSAGE_CONSTRAINTS);

        // invalid gender
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.INVALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, Gender.MESSAGE_CONSTRAINTS);

        // invalid height
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.INVALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, Height.MESSAGE_CONSTRAINTS);

        // invalid weight
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.INVALID_DESC_WEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, Weight.MESSAGE_CONSTRAINTS);
    }
}
