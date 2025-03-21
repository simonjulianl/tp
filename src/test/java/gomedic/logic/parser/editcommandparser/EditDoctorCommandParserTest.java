package gomedic.logic.parser.editcommandparser;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.editcommand.EditDoctorCommand;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.CommandParserTestUtil;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.DoctorId;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.editdescriptorbuilder.EditDoctorDescriptorBuilder;

public class EditDoctorCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditDoctorCommand.MESSAGE_USAGE);

    // Use doctor id 1 as the control id for comparison
    private static final Id targetId = new DoctorId(1);
    private static final String PREFIXED_TARGET_ID = " " + CliSyntax.PREFIX_ID + targetId;

    private final EditDoctorCommandParser parser = new EditDoctorCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, PREFIXED_TARGET_ID, EditDoctorCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "i/HEHE"
                + CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR, MESSAGE_INVALID_FORMAT);

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
        // invalid department
        CommandParserTestUtil.assertParseFailure(parser, ""
                + PREFIXED_TARGET_ID
                + CommandTestUtil.INVALID_DESC_DEPARTMENT_MAIN_DOCTOR, Department.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid department
        CommandParserTestUtil.assertParseFailure(parser,
                PREFIXED_TARGET_ID + CommandTestUtil.INVALID_PHONE_DESC
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        CommandParserTestUtil.assertParseFailure(parser,
                PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                        + CommandTestUtil.INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser,
                PREFIXED_TARGET_ID + CommandTestUtil.INVALID_NAME_DESC
                        + CommandTestUtil.INVALID_DESC_DEPARTMENT_MAIN_DOCTOR,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = PREFIXED_TARGET_ID
                + CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR;

        EditDoctorCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder()
                .withName(TypicalPersons.MAIN_DOCTOR.getName().fullName)
                .withPhone(TypicalPersons.MAIN_DOCTOR.getPhone().value)
                .withDepartment(TypicalPersons.MAIN_DOCTOR.getDepartment().departmentName)
                .build();
        EditDoctorCommand expectedCommand = new EditDoctorCommand(targetId, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                + CommandTestUtil.VALID_DESC_DEPARTMENT_OTHER_DOCTOR;
        EditDoctorCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder()
                .withPhone(TypicalPersons.MAIN_DOCTOR.getPhone().value)
                .withDepartment(TypicalPersons.OTHER_DOCTOR.getDepartment().departmentName).build();
        EditDoctorCommand expectedCommand = new EditDoctorCommand(targetId, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR;
        EditDoctorCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder()
                .withName(TypicalPersons.MAIN_DOCTOR.getName().fullName)
                .build();
        EditDoctorCommand expectedCommand = new EditDoctorCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR;
        descriptor = new EditDoctorDescriptorBuilder().withPhone(TypicalPersons.MAIN_DOCTOR.getPhone().value).build();
        expectedCommand = new EditDoctorCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // department
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR;
        descriptor = new EditDoctorDescriptorBuilder()
                .withDepartment(TypicalPersons.MAIN_DOCTOR.getDepartment().departmentName).build();
        expectedCommand = new EditDoctorCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = PREFIXED_TARGET_ID
                + CommandTestUtil.VALID_DESC_DEPARTMENT_OTHER_DOCTOR
                + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR;

        EditDoctorCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder()
                .withDepartment(TypicalPersons.MAIN_DOCTOR.getDepartment().departmentName).build();
        EditDoctorCommand expectedCommand = new EditDoctorCommand(targetId, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other values specified other than phone
        String userInput = PREFIXED_TARGET_ID
                + CommandTestUtil.INVALID_PHONE_DESC
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR;
        EditDoctorCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder()
                .withPhone(TypicalPersons.MAIN_DOCTOR.getPhone().value)
                .build();
        EditDoctorCommand expectedCommand = new EditDoctorCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = PREFIXED_TARGET_ID
                + CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR
                + CommandTestUtil.INVALID_PHONE_DESC
                + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR;
        descriptor = new EditDoctorDescriptorBuilder().withPhone(TypicalPersons.MAIN_DOCTOR.getPhone().value)
                .withDepartment(TypicalPersons.MAIN_DOCTOR.getDepartment().departmentName)
                .withName(TypicalPersons.MAIN_DOCTOR.getName().fullName).build();
        expectedCommand = new EditDoctorCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
