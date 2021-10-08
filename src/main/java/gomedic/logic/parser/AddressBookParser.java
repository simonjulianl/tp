package gomedic.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.ClearCommand;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.DeleteCommand;
import gomedic.logic.commands.EditCommand;
import gomedic.logic.commands.ExitCommand;
import gomedic.logic.commands.FindCommand;
import gomedic.logic.commands.HelpCommand;
import gomedic.logic.commands.ListCommand;
import gomedic.logic.commands.addcommand.AddPersonCommand;
import gomedic.logic.parser.addcommandparser.AddPersonCommandParser;
import gomedic.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern GENERIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TYPED_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+ t/\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher genericMatcher = GENERIC_COMMAND_FORMAT.matcher(userInput.trim());
        final Matcher specificMatcher = TYPED_COMMAND_FORMAT.matcher(userInput.trim());

        if (!genericMatcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        String commandWord;
        String arguments;

        if (specificMatcher.matches()) {
            commandWord = specificMatcher.group("commandWord");
            arguments = specificMatcher.group("arguments");
        } else {
            commandWord = genericMatcher.group("commandWord");
            arguments = genericMatcher.group("arguments");
        }

        switch (commandWord) {

        case AddPersonCommand.COMMAND_WORD:
            return new AddPersonCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(Messages.getSuggestions(commandWord));
        }
    }
}
