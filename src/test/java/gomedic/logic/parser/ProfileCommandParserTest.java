package gomedic.logic.parser;

import static gomedic.testutil.TypicalUserProfile.MAIN_PROFILE;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.ProfileCommand;
import gomedic.model.activity.Description;
import gomedic.model.commonfield.Name;
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
                        + CommandTestUtil.VALID_DESC_DESCRIPTION_MAIN_PROFILE, new ProfileCommand(
                        expectedUserProfile.getName(),
                        expectedUserProfile.getDescription()));

        // multiple name - last name will be accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_NAME_OTHER_DOCTOR
                        + CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DESCRIPTION_MAIN_PROFILE, new ProfileCommand(
                        expectedUserProfile.getName(),
                        expectedUserProfile.getDescription()));

        // multiple descriptions - last descriptions accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.VALID_DESC_DESCRIPTION_OTHER_PROFILE
                        + CommandTestUtil.VALID_DESC_DESCRIPTION_MAIN_PROFILE, new ProfileCommand(
                        expectedUserProfile.getName(),
                        expectedUserProfile.getDescription()));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ProfileCommand.MESSAGE_USAGE);

        // missing name
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_DESCRIPTION_MAIN_PROFILE,
                expectedMessage);

        // missing description
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.INVALID_DESC_NAME_MAIN_DOCTOR
                        + CommandTestUtil.VALID_DESC_DESCRIPTION_MAIN_PROFILE, Name.MESSAGE_CONSTRAINTS);

        // invalid description
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_NAME_MAIN_PROFILE
                        + CommandTestUtil.INVALID_DESC_DESCRIPTION, Description.MESSAGE_CONSTRAINTS);
    }
}
