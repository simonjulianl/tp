package gomedic.logic.parser.viewcommandparser;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.viewcommand.ViewPatientCommand;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Id;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewPatientParser implements Parser<ViewPatientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewPatientCommand
     * and returns a ViewPatientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewPatientCommand parse(String args) throws ParseException {
        try {
            Id id = ParserUtil.parseId(args.toUpperCase());
            return new ViewPatientCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewPatientCommand.MESSAGE_USAGE), pe);
        }
    }

}
