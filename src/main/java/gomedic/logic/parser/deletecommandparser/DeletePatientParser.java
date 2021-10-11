package gomedic.logic.parser.deletecommandparser;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.deletecommand.DeletePatientCommand;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Id;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeletePatientParser implements Parser<DeletePatientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePatientCommand
     * and returns a DeletePatientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePatientCommand parse(String args) throws ParseException {
        try {
            Id id = ParserUtil.parseId(args);
            return new DeletePatientCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePatientCommand.MESSAGE_USAGE), pe);
        }
    }

}
