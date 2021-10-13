package gomedic.logic.parser;

import static java.util.Objects.requireNonNull;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.EditCommand;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Id;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final String INVALID_INPUT = "";
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
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
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditDoctorDescriptor editDoctorDescriptor = new EditCommand.EditDoctorDescriptor();
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
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(targetId, editDoctorDescriptor);
    }

}
