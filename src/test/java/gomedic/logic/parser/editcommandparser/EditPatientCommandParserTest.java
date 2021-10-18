package gomedic.logic.parser.editcommandparser;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.editcommand.EditPatientCommand;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.CommandParserTestUtil;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.patient.Age;
import gomedic.model.person.patient.BloodType;
import gomedic.model.person.patient.Gender;
import gomedic.model.person.patient.Height;
import gomedic.model.person.patient.PatientId;
import gomedic.model.person.patient.Weight;
import gomedic.model.tag.Tag;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.editdescriptorbuilder.EditPatientDescriptorBuilder;

public class EditPatientCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPatientCommand.MESSAGE_USAGE);

    // Use patient id 1 as the control id for comparison
    private static final Id targetId = new PatientId(1);
    private static final String PREFIXED_TARGET_ID = " " + CliSyntax.PREFIX_ID + targetId;

    private final EditPatientCommandParser parser = new EditPatientCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser,
            CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, PREFIXED_TARGET_ID, EditPatientCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "i/HEHE"
            + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "i/D001 badprefix/string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser,
            PREFIXED_TARGET_ID + CommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser,
            PREFIXED_TARGET_ID + CommandTestUtil.INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        // invalid age
        CommandParserTestUtil.assertParseFailure(parser, ""
            + PREFIXED_TARGET_ID
            + CommandTestUtil.INVALID_DESC_AGE_MAIN_PATIENT, Age.MESSAGE_CONSTRAINTS);
        // invalid bloodType
        CommandParserTestUtil.assertParseFailure(parser, ""
            + PREFIXED_TARGET_ID
            + CommandTestUtil.INVALID_DESC_BLOODTYPE_MAIN_PATIENT, BloodType.MESSAGE_CONSTRAINTS);
        // invalid gender
        CommandParserTestUtil.assertParseFailure(parser, ""
            + PREFIXED_TARGET_ID
            + CommandTestUtil.INVALID_DESC_GENDER_MAIN_PATIENT, Gender.MESSAGE_CONSTRAINTS);
        // invalid height
        CommandParserTestUtil.assertParseFailure(parser, ""
            + PREFIXED_TARGET_ID
            + CommandTestUtil.INVALID_DESC_HEIGHT_MAIN_PATIENT, Height.MESSAGE_CONSTRAINTS);
        // invalid weight
        CommandParserTestUtil.assertParseFailure(parser, ""
            + PREFIXED_TARGET_ID
            + CommandTestUtil.INVALID_DESC_WEIGHT_MAIN_PATIENT, Weight.MESSAGE_CONSTRAINTS);
        // invalid medicalConditions
        CommandParserTestUtil.assertParseFailure(parser, ""
            + PREFIXED_TARGET_ID
            + CommandTestUtil.INVALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT, Tag.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid age
        CommandParserTestUtil.assertParseFailure(parser,
            PREFIXED_TARGET_ID + CommandTestUtil.INVALID_PHONE_DESC
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        CommandParserTestUtil.assertParseFailure(parser,
            PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser,
            PREFIXED_TARGET_ID + CommandTestUtil.INVALID_NAME_DESC
                + CommandTestUtil.INVALID_DESC_AGE_MAIN_PATIENT,
            Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = PREFIXED_TARGET_ID
            + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withName(TypicalPersons.MAIN_PATIENT.getName().fullName)
            .withPhone(TypicalPersons.MAIN_PATIENT.getPhone().value)
            .withAge(TypicalPersons.MAIN_PATIENT.getAge().age)
            .withBloodType(TypicalPersons.MAIN_PATIENT.getBloodType().bloodType)
            .withGender(TypicalPersons.MAIN_PATIENT.getGender().gender)
            .withHeight(TypicalPersons.MAIN_PATIENT.getHeight().height)
            .withWeight(TypicalPersons.MAIN_PATIENT.getWeight().weight)
            .withMedicalConditions("heart failure")
            .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetId, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = PREFIXED_TARGET_ID
            + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT;
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withName(TypicalPersons.MAIN_PATIENT.getName().fullName)
            .withPhone(TypicalPersons.MAIN_PATIENT.getPhone().value)
            .withAge(TypicalPersons.MAIN_PATIENT.getAge().age)
            .withBloodType(TypicalPersons.MAIN_PATIENT.getBloodType().bloodType)
            .withGender(TypicalPersons.MAIN_PATIENT.getGender().gender)
            .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetId, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT;
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withName(TypicalPersons.MAIN_PATIENT.getName().fullName)
            .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT;
        descriptor = new EditPatientDescriptorBuilder().withPhone(TypicalPersons.MAIN_PATIENT.getPhone().value).build();
        expectedCommand = new EditPatientCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // age
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT;
        descriptor = new EditPatientDescriptorBuilder()
            .withAge(TypicalPersons.MAIN_PATIENT.getAge().age).build();
        expectedCommand = new EditPatientCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // bloodType
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT;
        descriptor = new EditPatientDescriptorBuilder()
            .withBloodType(TypicalPersons.MAIN_PATIENT.getBloodType().bloodType).build();
        expectedCommand = new EditPatientCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT;
        descriptor = new EditPatientDescriptorBuilder()
            .withGender(TypicalPersons.MAIN_PATIENT.getGender().gender).build();
        expectedCommand = new EditPatientCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // height
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT;
        descriptor = new EditPatientDescriptorBuilder()
            .withHeight(TypicalPersons.MAIN_PATIENT.getHeight().height).build();
        expectedCommand = new EditPatientCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // weight
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT;
        descriptor = new EditPatientDescriptorBuilder()
            .withWeight(TypicalPersons.MAIN_PATIENT.getWeight().weight).build();
        expectedCommand = new EditPatientCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // medicalConditions
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT;
        descriptor = new EditPatientDescriptorBuilder()
            .withMedicalConditions("heart failure").build();
        expectedCommand = new EditPatientCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = PREFIXED_TARGET_ID
            + CommandTestUtil.VALID_DESC_AGE_OTHER_PATIENT
            + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withAge(TypicalPersons.MAIN_PATIENT.getAge().age).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetId, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other values specified other than phone
        String userInput = PREFIXED_TARGET_ID
            + CommandTestUtil.INVALID_PHONE_DESC
            + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT;
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withPhone(TypicalPersons.MAIN_PATIENT.getPhone().value)
            .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = PREFIXED_TARGET_ID
            + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
            + CommandTestUtil.INVALID_PHONE_DESC
            + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
            + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT;
        descriptor = new EditPatientDescriptorBuilder().withPhone(TypicalPersons.MAIN_PATIENT.getPhone().value)
            .withAge(TypicalPersons.MAIN_PATIENT.getAge().age)
            .withName(TypicalPersons.MAIN_PATIENT.getName().fullName).build();
        expectedCommand = new EditPatientCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
