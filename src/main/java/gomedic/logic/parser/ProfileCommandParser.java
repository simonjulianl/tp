package gomedic.logic.parser;

import java.util.stream.Stream;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.ProfileCommand;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.activity.Description;
import gomedic.model.commonfield.Name;

/**
 * Parses input arguments and creates a new ProfileCommand object.
 */
public class ProfileCommandParser implements Parser<ProfileCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ProfileCommand
     * and returns a ProfileCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ProfileCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        CliSyntax.PREFIX_NAME,
                        CliSyntax.PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap,
                CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(
                            Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            ProfileCommand.MESSAGE_USAGE
                    ));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(CliSyntax.PREFIX_DESCRIPTION).get());

        return new ProfileCommand(name, description);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
