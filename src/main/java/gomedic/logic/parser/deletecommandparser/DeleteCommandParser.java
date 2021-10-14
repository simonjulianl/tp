package gomedic.logic.parser.deletecommandparser;

import gomedic.commons.core.Messages;
import gomedic.commons.core.index.Index;
import gomedic.logic.commands.deletecommand.DeletePersonCommand;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeletePersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePersonCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePersonCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE), pe);
        }
    }

}
