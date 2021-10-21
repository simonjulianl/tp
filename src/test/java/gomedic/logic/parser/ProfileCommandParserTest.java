package gomedic.logic.parser;

import static gomedic.testutil.TypicalUserProfile.MAIN_PROFILE;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.ProfileCommand;
import gomedic.model.commonfield.Name;
import gomedic.model.person.doctor.Department;
import gomedic.model.userprofile.UserProfile;

public class ProfileCommandParserTest {
    private final ProfileCommandParser parser = new ProfileCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        UserProfile expectedUserProfile = MAIN_PROFILE;

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.PREAMBLE_WHITESPACE
                        + CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_POSITION_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_MAIN_PROFILE, new ProfileCommand(
                        expectedUserProfile.getName(),
                        expectedUserProfile.getPosition(),
                        expectedUserProfile.getDepartment(),
                        expectedUserProfile.getOrganization()));

        // multiple name - last name will be accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_NAME_OTHER_DOCTOR
                        + CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_POSITION_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_MAIN_PROFILE, new ProfileCommand(
                        expectedUserProfile.getName(),
                        expectedUserProfile.getPosition(),
                        expectedUserProfile.getDepartment(),
                        expectedUserProfile.getOrganization()));

        // multiple position - last position accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_POSITION_OTHER_PROFILE
                        + CommandTestUtil.VALID_DESC_POSITION_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_MAIN_PROFILE, new ProfileCommand(
                        expectedUserProfile.getName(),
                        expectedUserProfile.getPosition(),
                        expectedUserProfile.getDepartment(),
                        expectedUserProfile.getOrganization()));

        // multiple department - last department accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_POSITION_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_OTHER_PROFILE
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_MAIN_PROFILE, new ProfileCommand(
                        expectedUserProfile.getName(),
                        expectedUserProfile.getPosition(),
                        expectedUserProfile.getDepartment(),
                        expectedUserProfile.getOrganization()));

        // multiple organization - last organization accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_POSITION_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_OTHER_PROFILE
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_MAIN_PROFILE, new ProfileCommand(
                        expectedUserProfile.getName(),
                        expectedUserProfile.getPosition(),
                        expectedUserProfile.getDepartment(),
                        expectedUserProfile.getOrganization()));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ProfileCommand.MESSAGE_USAGE);

        // missing name
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_POSITION_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_MAIN_PROFILE,
                expectedMessage);

        // missing position
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_MAIN_PROFILE,
                expectedMessage);

        // missing department
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_POSITION_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_MAIN_PROFILE,
                expectedMessage);

        // missing organization
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_POSITION_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_PROFILE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.INVALID_DESC_NAME_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_POSITION_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_MAIN_PROFILE, Name.MESSAGE_CONSTRAINTS);

        // invalid department
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_POSITION_MAIN_PROFILE
                        + CommandTestUtil.INVALID_DESC_DEPARTMENT_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_ORGANIZATION_MAIN_PROFILE, Department.MESSAGE_CONSTRAINTS);
    }
}
