package gomedic.logic.parser.editcommandparser;

import static java.util.Objects.requireNonNull;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.editcommand.EditActivityCommand;
import gomedic.logic.parser.ArgumentMultimap;
import gomedic.logic.parser.ArgumentTokenizer;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Id;

/**
 * Parses input arguments and creates a new EditActivityCommand object
 */
public class EditActivityCommandParser implements Parser<EditActivityCommand> {

    private static final String INVALID_INPUT = "";

    /**
     * Parses the given {@code String} of arguments in the context of the EditActivityCommand
     * and returns an EditActivityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditActivityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        CliSyntax.PREFIX_ID,
                        CliSyntax.PREFIX_START_TIME,
                        CliSyntax.PREFIX_END_TIME,
                        CliSyntax.PREFIX_TITLE,
                        CliSyntax.PREFIX_DESCRIPTION);

        Id targetId;

        try {
            targetId = ParserUtil.parseId(argMultimap.getValue(CliSyntax.PREFIX_ID).orElse(INVALID_INPUT).toUpperCase());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditActivityCommand.MESSAGE_USAGE), pe);
        }

        EditActivityCommand.EditActivityDescriptor editActivityDescriptor =
                new EditActivityCommand.EditActivityDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_START_TIME).isPresent()) {
            editActivityDescriptor.setStartTime(ParserUtil.parseTime(argMultimap.getValue(CliSyntax.PREFIX_START_TIME)
                    .get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_END_TIME).isPresent()) {
            editActivityDescriptor.setEndTime(ParserUtil.parseTime(argMultimap.getValue(CliSyntax.PREFIX_END_TIME)
                    .get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_TITLE).isPresent()) {
            editActivityDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(CliSyntax.PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_DESCRIPTION).isPresent()) {
            editActivityDescriptor
                    .setDescription(ParserUtil.parseDescription(argMultimap.getValue(CliSyntax.PREFIX_DESCRIPTION)
                            .get()));
        }

        if (!editActivityDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditActivityCommand.MESSAGE_NOT_EDITED);
        }

        return new EditActivityCommand(targetId, editActivityDescriptor);
    }

}
