package gomedic.logic.parser.addcommandparser;

import static gomedic.testutil.TypicalActivities.MEETING;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.addcommand.AddActivityCommand;
import gomedic.logic.parser.CommandParserTestUtil;
import gomedic.model.activity.Activity;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Time;

public class AddActivityParserTest {
    private final AddActivityCommandParser parser = new AddActivityCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Activity expectedActivity = MEETING;

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.PREAMBLE_WHITESPACE
                        + CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION, new AddActivityCommand(
                        expectedActivity.getStartTime(),
                        expectedActivity.getEndTime(),
                        expectedActivity.getTitle(),
                        expectedActivity.getDescription()));

        // multiple start time - last of the argument will be accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_START_TIME_PAPER_REVIEW
                        + CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION, new AddActivityCommand(
                        expectedActivity.getStartTime(),
                        expectedActivity.getEndTime(),
                        expectedActivity.getTitle(),
                        expectedActivity.getDescription()));

        // multiple end time
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_PAPER_REVIEW
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION, new AddActivityCommand(
                        expectedActivity.getStartTime(),
                        expectedActivity.getEndTime(),
                        expectedActivity.getTitle(),
                        expectedActivity.getDescription()));

        // multiple title, last title accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_PAPER_REVIEW
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION, new AddActivityCommand(
                        expectedActivity.getStartTime(),
                        expectedActivity.getEndTime(),
                        expectedActivity.getTitle(),
                        expectedActivity.getDescription()));

        // multiple desc, last desc accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION, new AddActivityCommand(
                        expectedActivity.getStartTime(),
                        expectedActivity.getEndTime(),
                        expectedActivity.getTitle(),
                        expectedActivity.getDescription()));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Activity expectedActivity = MEETING;

        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING, new AddActivityCommand(
                        expectedActivity.getStartTime(),
                        expectedActivity.getEndTime(),
                        expectedActivity.getTitle(),
                        new Description("")));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddActivityCommand.MESSAGE_USAGE);

        // missing start time
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION,
                expectedMessage);

        // missing end time
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION,
                expectedMessage);

        // missing title
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid time
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.INVALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION, Time.MESSAGE_CONSTRAINTS);

        // invalid title
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.INVALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION, Title.MESSAGE_CONSTRAINTS);

        // invalid desc
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.INVALID_DESC_DESCRIPTION, Description.MESSAGE_CONSTRAINTS);
    }
}
