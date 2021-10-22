package gomedic.logic.parser.addcommandparser;

import java.util.stream.Stream;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.addcommand.AddAppointmentCommand;
import gomedic.logic.parser.ArgumentMultimap;
import gomedic.logic.parser.ArgumentTokenizer;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.Prefix;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Time;

/**
 * Parses input arguments and creates a new AddActivityCommand object.
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        CliSyntax.PREFIX_ID,
                        CliSyntax.PREFIX_START_TIME,
                        CliSyntax.PREFIX_END_TIME,
                        CliSyntax.PREFIX_TITLE,
                        CliSyntax.PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap,
                CliSyntax.PREFIX_ID,
                CliSyntax.PREFIX_START_TIME,
                CliSyntax.PREFIX_END_TIME,
                CliSyntax.PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(
                            Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            AddAppointmentCommand.MESSAGE_USAGE
                    ));
        }
        Id patientId = ParserUtil.parseId(argMultimap.getValue(CliSyntax.PREFIX_ID).get());
        Time startTime = ParserUtil.parseTime(argMultimap.getValue(CliSyntax.PREFIX_START_TIME).get());
        Time endTime = ParserUtil.parseTime(argMultimap.getValue(CliSyntax.PREFIX_END_TIME).get());
        Title title = ParserUtil.parseTitle(argMultimap.getValue(CliSyntax.PREFIX_TITLE).get());
        Description description = ParserUtil
                .parseDescription(
                        argMultimap.getValue(CliSyntax.PREFIX_DESCRIPTION)
                                .orElse("")
                );

        return new AddAppointmentCommand(patientId, startTime, endTime, title, description);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
