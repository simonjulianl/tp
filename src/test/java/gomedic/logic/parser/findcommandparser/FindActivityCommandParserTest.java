package gomedic.logic.parser.findcommandparser;

import static gomedic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static gomedic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.findcommand.FindActivityCommand;
import gomedic.model.activity.Activity;
import gomedic.model.util.ActivityTitleContainsKeywordsPredicate;

public class FindActivityCommandParserTest {

    private final FindActivityCommandParser parser = new FindActivityCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindActivityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindDoctorCommand() {
        // no leading and trailing whitespaces
        FindActivityCommand expectedFindActivityCommand =
                new FindActivityCommand(new
                        ActivityTitleContainsKeywordsPredicate<Activity>(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "ti/Alice Bob", expectedFindActivityCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " ti/ Alice  Bob ", expectedFindActivityCommand);

    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no flag supplied
        assertParseFailure(parser, " alice bob",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindActivityCommand.MESSAGE_USAGE));

        // multiple flags supplied
        assertParseFailure(parser, " ti/ alice ti/diabetes ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindActivityCommand.MESSAGE_USAGE));

    }

}
