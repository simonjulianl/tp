package gomedic.logic.parser.editcommandparser;

import static java.util.Objects.requireNonNull;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.editcommand.EditDoctorCommand;
import gomedic.logic.parser.ArgumentMultimap;
import gomedic.logic.parser.ArgumentTokenizer;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Id;

/**
 * Parses input arguments and creates a new EditDoctorCommand object
 */
public class EditDoctorCommandParser implements Parser<EditDoctorCommand> {

    private static final String INVALID_INPUT = "";
    /**
     * Parses the given {@code String} of arguments in the context of the EditDoctorCommand
     * and returns an EditDoctorCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDoctorCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        CliSyntax.PREFIX_ID,
                        CliSyntax.PREFIX_NAME,
                        CliSyntax.PREFIX_PHONE,
                        CliSyntax.PREFIX_DEPARTMENT);

        Id targetId;

        try {
            targetId = ParserUtil.parseId(argMultimap.getValue(CliSyntax.PREFIX_ID).orElse(INVALID_INPUT));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditDoctorCommand.MESSAGE_USAGE), pe);
        }

        EditDoctorCommand.EditDoctorDescriptor editDoctorDescriptor = new EditDoctorCommand.EditDoctorDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            editDoctorDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_PHONE).isPresent()) {
            editDoctorDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_DEPARTMENT).isPresent()) {
            editDoctorDescriptor
                    .setDepartment(ParserUtil.parseDepartment(argMultimap.getValue(CliSyntax.PREFIX_DEPARTMENT).get()));
        }

        if (!editDoctorDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDoctorCommand.MESSAGE_NOT_EDITED);
        }

        return new EditDoctorCommand(targetId, editDoctorDescriptor);
    }

}
