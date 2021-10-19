package gomedic.logic.parser.findcommandparser;

import static gomedic.logic.parser.CliSyntax.AGE;
import static gomedic.logic.parser.CliSyntax.BLOOD_TYPE;
import static gomedic.logic.parser.CliSyntax.GENDER;
import static gomedic.logic.parser.CliSyntax.HEIGHT;
import static gomedic.logic.parser.CliSyntax.NAME;
import static gomedic.logic.parser.CliSyntax.MEDICAL_CONDITION;
import static gomedic.logic.parser.CliSyntax.PHONE_NUMBER;
import static gomedic.logic.parser.CliSyntax.WEIGHT;

import java.util.Arrays;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.findcommand.FindPatientCommand;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.util.AgeContainsKeywordsPredicate;
import gomedic.model.util.BloodTypeContainsKeywordsPredicate;
import gomedic.model.util.GenderContainsKeywordsPredicate;
import gomedic.model.util.HeightContainsKeywordsPredicate;
import gomedic.model.util.NameContainsKeywordsPredicate;
import gomedic.model.util.MedicalConditionContainsKeywordsPredicate;
import gomedic.model.util.PhoneNumberContainsKeywordsPredicate;
import gomedic.model.util.WeightContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindPatientCommand object
 */
public class FindPatientCommandParser implements Parser<FindPatientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPatientCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindPatientCommand.MESSAGE_USAGE));
        }

        // First find out which field it is supposed to match to
        // then create a PatientCommand with the argument containing the corresponding
        // Predicate
        if (trimmedArgs.indexOf("/") == -1) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindPatientCommand.MESSAGE_USAGE));
        }
        String[] fieldAndArguments = trimmedArgs.split("/");
        if (fieldAndArguments.length != 2) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindPatientCommand.MESSAGE_USAGE));
        }
        fieldAndArguments[1] = fieldAndArguments[1].trim();
        String[] argumentKeywords = fieldAndArguments[1].split("\\s+");

        String field = fieldAndArguments[0];

        switch (field) {

        case NAME:
            return new FindPatientCommand(new NameContainsKeywordsPredicate<>(Arrays.asList(argumentKeywords)));

        case PHONE_NUMBER:
            return new FindPatientCommand(new PhoneNumberContainsKeywordsPredicate<>(Arrays.asList(argumentKeywords)));

        case AGE:
            return new FindPatientCommand(new AgeContainsKeywordsPredicate<>(Arrays.asList(argumentKeywords)));

        case BLOOD_TYPE:
            return new FindPatientCommand(new BloodTypeContainsKeywordsPredicate<>(Arrays.asList(argumentKeywords)));

        case GENDER:
            return new FindPatientCommand(new GenderContainsKeywordsPredicate<>(Arrays.asList(argumentKeywords)));

        case HEIGHT:
            return new FindPatientCommand(new HeightContainsKeywordsPredicate<>(Arrays.asList(argumentKeywords)));

        case WEIGHT:
            return new FindPatientCommand(new WeightContainsKeywordsPredicate<>(Arrays.asList(argumentKeywords)));

        case MEDICAL_CONDITION:
            return new FindPatientCommand(new MedicalConditionContainsKeywordsPredicate<>(
                    Arrays.asList(argumentKeywords)));

        default:
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindPatientCommand.MESSAGE_USAGE));        }
    }
}
