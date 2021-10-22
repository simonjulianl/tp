package gomedic.logic.parser;

import java.util.stream.Stream;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.ReferralCommand;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Id;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ReferralCommandParser implements Parser<ReferralCommand> {
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ReferralCommand
     * and returns a ReferralCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReferralCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ReferralCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        CliSyntax.PREFIX_TITLE,
                        CliSyntax.PREFIX_PATIENT_ID,
                        CliSyntax.PREFIX_DOCTOR_ID,
                        CliSyntax.PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap,
                CliSyntax.PREFIX_TITLE,
                CliSyntax.PREFIX_PATIENT_ID,
                CliSyntax.PREFIX_DOCTOR_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(
                            Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            ReferralCommand.MESSAGE_USAGE
                    ));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(CliSyntax.PREFIX_TITLE).get());
        Id doctorId = ParserUtil.parseId(argMultimap.getValue(CliSyntax.PREFIX_DOCTOR_ID).get());
        Id patientId = ParserUtil.parseId(argMultimap.getValue(CliSyntax.PREFIX_PATIENT_ID).get());

        Description description = ParserUtil
                .parseDescription(
                        argMultimap.getValue(CliSyntax.PREFIX_DESCRIPTION)
                                .orElse("")
                );

        return new ReferralCommand(title, doctorId, patientId, description);
    }
}
