package gomedic.logic.parser.deletecommandparser;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.deletecommand.DeleteDoctorCommand;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Id;

/**
 * Parses input arguments and creates a new DeleteDoctorCommand object
 */
public class DeleteDoctorParser implements Parser<DeleteDoctorCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteDoctorCommand
     * and returns a DeleteDoctorCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteDoctorCommand parse(String args) throws ParseException {
        try {
            Id id = ParserUtil.parseId(args);
            return new DeleteDoctorCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteDoctorCommand.MESSAGE_USAGE), pe);
        }
    }
}
