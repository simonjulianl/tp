package gomedic.logic.parser.findcommandparser;

import static gomedic.logic.parser.CliSyntax.ACTIVITY_TITLE;

import java.util.Arrays;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.findcommand.FindActivityCommand;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.util.ActivityTitleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindActivityCommand object
 */
public class FindActivityCommandParser implements Parser<FindActivityCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindActivityCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindActivityCommand.MESSAGE_USAGE));
        }

        // First find out which field it is supposed to match to
        // then create an ActivityCommand with the argument containing the corresponding
        // Predicate
        if (trimmedArgs.indexOf("/") == -1) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindActivityCommand.MESSAGE_USAGE));
        }
        String[] fieldAndArguments = trimmedArgs.split("/");
        if (fieldAndArguments.length != 2) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindActivityCommand.MESSAGE_USAGE));
        }
        fieldAndArguments[1] = fieldAndArguments[1].trim();
        String[] argumentKeywords = fieldAndArguments[1].split("\\s+");

        String field = fieldAndArguments[0];

        switch (field) {

        case ACTIVITY_TITLE:
            return new FindActivityCommand(
                    new ActivityTitleContainsKeywordsPredicate<>(Arrays.asList(argumentKeywords)));
        default:
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindActivityCommand.MESSAGE_USAGE));

        }

    }
}
