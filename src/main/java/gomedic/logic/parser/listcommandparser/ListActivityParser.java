package gomedic.logic.parser.listcommandparser;

import gomedic.logic.commands.listcommand.ListActivityCommand;
import gomedic.logic.parser.ArgumentMultimap;
import gomedic.logic.parser.ArgumentTokenizer;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListActivityCommand object.
 */
public class ListActivityParser implements Parser<ListActivityCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListActivityCommand
     * and returns a ListActivityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListActivityCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        CliSyntax.PREFIX_SORT_FLAG,
                        CliSyntax.PREFIX_PERIOD_FLAG);

        ListActivityCommand.Sort sortFlag = ListActivityCommand.Sort.ID;
        ListActivityCommand.Period periodFlag = ListActivityCommand.Period.ALL;

        try {
            if (argMultimap.getValue(CliSyntax.PREFIX_SORT_FLAG).isPresent()) {
                sortFlag = ParserUtil
                        .parseSortActivityFlags(argMultimap.getValue(CliSyntax.PREFIX_SORT_FLAG).get());
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    ListActivityCommand.Sort.MESSAGE_CONSTRAINTS
                            + "\n"
                            + ListActivityCommand.MESSAGE_USAGE);
        }

        try {
            if (argMultimap.getValue(CliSyntax.PREFIX_PERIOD_FLAG).isPresent()) {
                periodFlag = ParserUtil
                        .parsePeriodActivityFlags(argMultimap.getValue(CliSyntax.PREFIX_PERIOD_FLAG).get());
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    ListActivityCommand.Period.MESSAGE_CONSTRAINTS
                            + "\n"
                            + ListActivityCommand.MESSAGE_USAGE);
        }

        return new ListActivityCommand(sortFlag, periodFlag);
    }
}
