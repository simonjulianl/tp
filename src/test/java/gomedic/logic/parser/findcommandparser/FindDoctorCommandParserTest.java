package gomedic.logic.parser.findcommandparser;

import static gomedic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static gomedic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.findcommand.FindDoctorCommand;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.util.NameContainsKeywordsPredicate;

public class FindDoctorCommandParserTest {

    private final FindDoctorCommandParser parser = new FindDoctorCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindDoctorCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindDoctorCommand() {
        // no leading and trailing whitespaces
        FindDoctorCommand expectedFindDoctorCommand =
                new FindDoctorCommand(new NameContainsKeywordsPredicate<Doctor>(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindDoctorCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ Alice  Bob ", expectedFindDoctorCommand);

    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no flag supplied
        assertParseFailure(parser, " alice bob",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindDoctorCommand.MESSAGE_USAGE));

        // multiple flags supplied
        assertParseFailure(parser, " n/ alice m/diabetes ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindDoctorCommand.MESSAGE_USAGE));

    }

}
