package gomedic.logic.parser.editcommandparser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.editcommand.EditPatientCommand;
import gomedic.logic.parser.ArgumentMultimap;
import gomedic.logic.parser.ArgumentTokenizer;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Id;
import gomedic.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditPatientCommand object
 */
public class EditPatientCommandParser implements Parser<EditPatientCommand> {

    private static final String INVALID_INPUT = "";
    /**
     * Parses the given {@code String} of arguments in the context of the EditPatientCommand
     * and returns an EditPatientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPatientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_ID,
                CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_PHONE,
                CliSyntax.PREFIX_AGE,
                CliSyntax.PREFIX_BLOODTYPE,
                CliSyntax.PREFIX_GENDER,
                CliSyntax.PREFIX_HEIGHT,
                CliSyntax.PREFIX_WEIGHT,
                CliSyntax.PREFIX_MEDICALCONDITIONS);

        Id targetId;

        try {
            targetId = ParserUtil.parseId(argMultimap.getValue(CliSyntax.PREFIX_ID).orElse(INVALID_INPUT));
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPatientCommand.MESSAGE_USAGE), pe);
        }

        EditPatientCommand.EditPatientDescriptor editPatientDescriptor = new EditPatientCommand.EditPatientDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            editPatientDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_PHONE).isPresent()) {
            editPatientDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_AGE).isPresent()) {
            editPatientDescriptor
                .setAge(ParserUtil.parseAge(argMultimap.getValue(CliSyntax.PREFIX_AGE).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_BLOODTYPE).isPresent()) {
            editPatientDescriptor
                .setBloodType(ParserUtil.parseBloodType(argMultimap.getValue(CliSyntax.PREFIX_BLOODTYPE).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_GENDER).isPresent()) {
            editPatientDescriptor
                .setGender(ParserUtil.parseGender(argMultimap.getValue(CliSyntax.PREFIX_GENDER).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_HEIGHT).isPresent()) {
            editPatientDescriptor
                .setHeight(ParserUtil.parseHeight(argMultimap.getValue(CliSyntax.PREFIX_HEIGHT).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_WEIGHT).isPresent()) {
            editPatientDescriptor
                .setWeight(ParserUtil.parseWeight(argMultimap.getValue(CliSyntax.PREFIX_WEIGHT).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(CliSyntax.PREFIX_MEDICALCONDITIONS))
            .ifPresent(editPatientDescriptor::setMedicalConditions);

        if (!editPatientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPatientCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPatientCommand(targetId, editPatientDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
