package gomedic.logic.parser.findcommandparser;

import static gomedic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static gomedic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import gomedic.logic.commands.findcommand.FindPatientCommand;
import gomedic.commons.core.Messages;
import gomedic.model.person.patient.Patient;
import gomedic.model.util.NameContainsKeywordsPredicate;

public class FindPatientCommandParserTest {

    private final FindPatientCommandParser parser = new FindPatientCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindPatientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindDoctorCommand() {
        // no leading and trailing whitespaces
        FindPatientCommand expectedFindPatientCommand =
                new FindPatientCommand(new NameContainsKeywordsPredicate<Patient>(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindPatientCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ Alice  Bob ", expectedFindPatientCommand);

    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no flag supplied
        assertParseFailure(parser, " alice bob",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindPatientCommand.MESSAGE_USAGE));

        // multiple flags supplied
        assertParseFailure(parser, " n/ alice m/diabetes ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindPatientCommand.MESSAGE_USAGE));

    }

}
