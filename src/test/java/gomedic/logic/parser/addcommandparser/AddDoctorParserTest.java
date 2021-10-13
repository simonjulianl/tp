package gomedic.logic.parser.addcommandparser;

import static gomedic.testutil.TypicalPersons.MAIN_DOCTOR;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.addcommand.AddDoctorCommand;
import gomedic.logic.parser.CommandParserTestUtil;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.Doctor;


public class AddDoctorParserTest {
    private final AddDoctorCommandParser parser = new AddDoctorCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Doctor expectedDoctor = MAIN_DOCTOR;

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.PREAMBLE_WHITESPACE
                        + CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR, new AddDoctorCommand(
                        expectedDoctor.getName(),
                        expectedDoctor.getPhone(),
                        expectedDoctor.getDepartment()));

        // multiple name - last name will be accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_NAME_OTHER_DOCTOR
                        + CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR, new AddDoctorCommand(
                        expectedDoctor.getName(),
                        expectedDoctor.getPhone(),
                        expectedDoctor.getDepartment()));

        // multiple phones - last phone accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_PHONE_OTHER_DOCTOR
                        + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR, new AddDoctorCommand(
                        expectedDoctor.getName(),
                        expectedDoctor.getPhone(),
                        expectedDoctor.getDepartment()));

        // multiple department, last department accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_OTHER_DOCTOR
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR, new AddDoctorCommand(
                        expectedDoctor.getName(),
                        expectedDoctor.getPhone(),
                        expectedDoctor.getDepartment()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddDoctorCommand.MESSAGE_USAGE);

        // missing name
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR,
                expectedMessage);

        // missing phone
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR,
                expectedMessage);

        // missing department
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.INVALID_DESC_NAME_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR
                        + CommandTestUtil.INVALID_DESC_PHONE_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR, Phone.MESSAGE_CONSTRAINTS);

        // invalid department
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                        + CommandTestUtil.INVALID_DESC_DEPARTMENT_MAIN_DOCTOR, Department.MESSAGE_CONSTRAINTS);
    }
}
