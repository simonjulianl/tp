package gomedic.logic.parser.findcommandparser;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.findcommand.FindActivityCommand;
import gomedic.logic.commands.findcommand.FindDoctorCommand;
import gomedic.logic.commands.findcommand.FindPatientCommand;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.util.ActivityTitleContainsKeywordsPredicate;
import gomedic.model.util.DepartmentContainsKeywordsPredicate;
import gomedic.model.util.NameContainsKeywordsPredicate;
import gomedic.model.util.PhoneNumberContainsKeywordsPredicate;


import java.util.Arrays;

import static gomedic.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new FindActivityCommand object
 */
public class FindActivityCommandParser {

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
        String[] fieldAndArguments = trimmedArgs.split("/");
        String[] argumentKeywords = fieldAndArguments[1].split("\\s+");

        String field = fieldAndArguments[0];

        switch (field) {
            case ACTIVITY_TITLE:
                return new FindActivityCommand(new ActivityTitleContainsKeywordsPredicate<>(Arrays.asList(argumentKeywords)));
            default:
                throw new ParseException(
                        String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindDoctorCommand.MESSAGE_USAGE));
        }

    }
}
