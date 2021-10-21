package gomedic.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.ExitCommand;
import gomedic.logic.commands.FindCommand;
import gomedic.logic.commands.HelpCommand;
import gomedic.logic.commands.ProfileCommand;
import gomedic.logic.commands.addcommand.AddActivityCommand;
import gomedic.logic.commands.addcommand.AddAppointmentCommand;
import gomedic.logic.commands.addcommand.AddDoctorCommand;
import gomedic.logic.commands.addcommand.AddPatientCommand;
import gomedic.logic.commands.clearcommand.ClearActivityCommand;
import gomedic.logic.commands.clearcommand.ClearCommand;
import gomedic.logic.commands.clearcommand.ClearDoctorCommand;
import gomedic.logic.commands.clearcommand.ClearPatientCommand;
import gomedic.logic.commands.deletecommand.DeleteActivityCommand;
import gomedic.logic.commands.deletecommand.DeleteDoctorCommand;
import gomedic.logic.commands.deletecommand.DeletePatientCommand;
import gomedic.logic.commands.editcommand.EditActivityCommand;
import gomedic.logic.commands.editcommand.EditDoctorCommand;
import gomedic.logic.commands.editcommand.EditPatientCommand;
import gomedic.logic.commands.listcommand.ListActivityCommand;
import gomedic.logic.commands.listcommand.ListDoctorCommand;
import gomedic.logic.commands.listcommand.ListPatientCommand;
import gomedic.logic.parser.addcommandparser.AddActivityCommandParser;
import gomedic.logic.parser.addcommandparser.AddAppointmentCommandParser;
import gomedic.logic.parser.addcommandparser.AddDoctorCommandParser;
import gomedic.logic.parser.addcommandparser.AddPatientCommandParser;
import gomedic.logic.parser.deletecommandparser.DeleteActivityParser;
import gomedic.logic.parser.deletecommandparser.DeleteDoctorParser;
import gomedic.logic.parser.deletecommandparser.DeletePatientParser;
import gomedic.logic.parser.editcommandparser.EditActivityCommandParser;
import gomedic.logic.parser.editcommandparser.EditDoctorCommandParser;
import gomedic.logic.parser.editcommandparser.EditPatientCommandParser;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.logic.parser.listcommandparser.ListActivityParser;

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
            Pattern.compile("(?<commandWord>\\S+\\st/\\S+)(?<arguments>.*)");

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

        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        case AddActivityCommand.COMMAND_WORD:
            return new AddActivityCommandParser().parse(arguments);

        case AddDoctorCommand.COMMAND_WORD:
            return new AddDoctorCommandParser().parse(arguments);

        case AddPatientCommand.COMMAND_WORD:
            return new AddPatientCommandParser().parse(arguments);

        case EditDoctorCommand.COMMAND_WORD:
            return new EditDoctorCommandParser().parse(arguments);

        case EditPatientCommand.COMMAND_WORD:
            return new EditPatientCommandParser().parse(arguments);

        case EditActivityCommand.COMMAND_WORD:
            return new EditActivityCommandParser().parse(arguments);

        case DeleteActivityCommand.COMMAND_WORD:
            return new DeleteActivityParser().parse(arguments);

        case DeleteDoctorCommand.COMMAND_WORD:
            return new DeleteDoctorParser().parse(arguments);

        case DeletePatientCommand.COMMAND_WORD:
            return new DeletePatientParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearActivityCommand.COMMAND_WORD:
            return new ClearActivityCommand();

        case ClearDoctorCommand.COMMAND_WORD:
            return new ClearDoctorCommand();

        case ClearPatientCommand.COMMAND_WORD:
            return new ClearPatientCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListActivityCommand.COMMAND_WORD:
            return new ListActivityParser().parse(arguments);

        case ListDoctorCommand.COMMAND_WORD:
            return new ListDoctorCommand();

        case ListPatientCommand.COMMAND_WORD:
            return new ListPatientCommand();

        case ProfileCommand.COMMAND_WORD:
            return new ProfileCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(Messages.getSuggestions(commandWord));
        }
    }
}
