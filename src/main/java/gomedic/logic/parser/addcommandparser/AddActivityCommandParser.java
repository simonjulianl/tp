package gomedic.logic.parser.addcommandparser;

import java.util.stream.Stream;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.addcommand.AddActivityCommand;
import gomedic.logic.parser.ArgumentMultimap;
import gomedic.logic.parser.ArgumentTokenizer;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.Prefix;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Time;

/**
 * Parses input arguments and creates a new AddActivityCommand object.
 */
public class AddActivityCommandParser implements Parser<AddActivityCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddActivityCommand
     * and returns an AddActivityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddActivityCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        CliSyntax.PREFIX_START_TIME,
                        CliSyntax.PREFIX_END_TIME,
                        CliSyntax.PREFIX_TITLE,
                        CliSyntax.PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap,
                CliSyntax.PREFIX_START_TIME,
                CliSyntax.PREFIX_END_TIME,
                CliSyntax.PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(
                            Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            AddActivityCommand.MESSAGE_USAGE
                    ));
        }

        Time startTime = ParserUtil.parseTime(argMultimap.getValue(CliSyntax.PREFIX_START_TIME).get());
        Time endTime = ParserUtil.parseTime(argMultimap.getValue(CliSyntax.PREFIX_END_TIME).get());
        Title title = ParserUtil.parseTitle(argMultimap.getValue(CliSyntax.PREFIX_TITLE).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(CliSyntax.PREFIX_DESCRIPTION).get());

        return new AddActivityCommand(startTime, endTime, title, description);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
