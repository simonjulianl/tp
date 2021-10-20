package gomedic.logic.parser.editcommandparser;


import static gomedic.testutil.TypicalActivities.MEETING;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.editcommand.EditActivityCommand;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.CommandParserTestUtil;
import gomedic.model.activity.ActivityId;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Time;
import gomedic.testutil.editdescriptorbuilder.EditActivityDescriptorBuilder;

class EditActivityCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditActivityCommand.MESSAGE_USAGE);

    // Use doctor id 1 as the control id for comparison
    private static final Id targetId = new ActivityId(1);
    private static final String PREFIXED_TARGET_ID = " " + CliSyntax.PREFIX_ID + targetId;

    private final EditActivityCommandParser parser = new EditActivityCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_START_TIME_MEETING, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, PREFIXED_TARGET_ID, EditActivityCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "i/HIHI"
                + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "i/A001 badprefix/string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid time
        CommandParserTestUtil.assertParseFailure(parser,
                PREFIXED_TARGET_ID + CommandTestUtil.INVALID_DESC_START_TIME_MEETING, Time.MESSAGE_CONSTRAINTS);
        // invalid title
        CommandParserTestUtil.assertParseFailure(parser,
                PREFIXED_TARGET_ID + CommandTestUtil.INVALID_DESC_TITLE_MEETING, Title.MESSAGE_CONSTRAINTS);
        // invalid description
        CommandParserTestUtil.assertParseFailure(parser, ""
                + PREFIXED_TARGET_ID
                + CommandTestUtil.INVALID_DESC_DESCRIPTION, Description.MESSAGE_CONSTRAINTS);

        // invalid time followed by valid title
        CommandParserTestUtil.assertParseFailure(parser,
                PREFIXED_TARGET_ID + CommandTestUtil.INVALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING, Time.MESSAGE_CONSTRAINTS);

        // valid time followed by invalid time, where both are start time. In this, the final value is taken.
        CommandParserTestUtil.assertParseFailure(parser,
                PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.INVALID_DESC_START_TIME_MEETING, Time.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the final invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser,
                PREFIXED_TARGET_ID + CommandTestUtil.INVALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION
                        + CommandTestUtil.INVALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.INVALID_DESC_DESCRIPTION,
                Time.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = PREFIXED_TARGET_ID
                + CommandTestUtil.VALID_DESC_START_TIME_MEETING
                + CommandTestUtil.VALID_DESC_TITLE_MEETING;

        EditActivityCommand.EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder()
                .withStartTime(MEETING.getStartTime().toString())
                .withTitle(MEETING.getTitle().toString())
                .build();
        EditActivityCommand expectedCommand = new EditActivityCommand(targetId, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_START_TIME_MEETING
                + CommandTestUtil.VALID_DESC_TITLE_MEETING;
        EditActivityCommand.EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder()
                .withStartTime(MEETING.getStartTime().toString())
                .withTitle(MEETING.getTitle().toString())
                .build();
        EditActivityCommand expectedCommand = new EditActivityCommand(targetId, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // Time
        String userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_START_TIME_MEETING;
        EditActivityCommand.EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder()
                .withStartTime(MEETING.getStartTime().toString())
                .build();
        EditActivityCommand expectedCommand = new EditActivityCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        //Title
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_TITLE_MEETING;
        descriptor = new EditActivityDescriptorBuilder()
                .withTitle(MEETING.getTitle().toString())
                .build();
        expectedCommand = new EditActivityCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // Desc
        userInput = PREFIXED_TARGET_ID + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION;
        descriptor = new EditActivityDescriptorBuilder()
                .withDescription(MEETING.getDescription().toString())
                .build();
        expectedCommand = new EditActivityCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = PREFIXED_TARGET_ID
                + CommandTestUtil.VALID_DESC_START_TIME_PAPER_REVIEW
                + CommandTestUtil.VALID_DESC_START_TIME_MEETING;

        EditActivityCommand.EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder()
                .withStartTime(MEETING.getStartTime().toString())
                .build();
        EditActivityCommand expectedCommand = new EditActivityCommand(targetId, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other values specified other than start time
        String userInput = PREFIXED_TARGET_ID
                + CommandTestUtil.INVALID_DESC_START_TIME_MEETING
                + CommandTestUtil.VALID_DESC_START_TIME_MEETING;
        EditActivityCommand.EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder()
                .withStartTime(MEETING.getStartTime().toString())
                .build();
        EditActivityCommand expectedCommand = new EditActivityCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = PREFIXED_TARGET_ID
                + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION
                + CommandTestUtil.INVALID_DESC_START_TIME_MEETING
                + CommandTestUtil.VALID_DESC_START_TIME_MEETING
                + CommandTestUtil.VALID_DESC_TITLE_MEETING;
        descriptor = new EditActivityDescriptorBuilder()
                .withStartTime(MEETING.getStartTime().toString())
                .withTitle(MEETING.getTitle().toString())
                .withDescription(MEETING.getDescription().toString()).build();
        expectedCommand = new EditActivityCommand(targetId, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
