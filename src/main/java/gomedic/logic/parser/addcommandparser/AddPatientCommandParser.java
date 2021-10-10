package gomedic.logic.parser.addcommandparser;

import java.util.Set;
import java.util.stream.Stream;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.addcommand.AddPatientCommand;
import gomedic.logic.parser.ArgumentMultimap;
import gomedic.logic.parser.ArgumentTokenizer;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.Prefix;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.patient.Age;
import gomedic.model.person.patient.BloodType;
import gomedic.model.person.patient.Gender;
import gomedic.model.person.patient.Height;
import gomedic.model.person.patient.Weight;
import gomedic.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddDoctorCommand object.
 */
public class AddPatientCommandParser implements Parser<AddPatientCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPatientCommand
     * and returns an AddPatientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPatientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(
                args,
                CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_PHONE,
                CliSyntax.PREFIX_AGE,
                CliSyntax.PREFIX_BLOODTYPE,
                CliSyntax.PREFIX_GENDER,
                CliSyntax.PREFIX_HEIGHT,
                CliSyntax.PREFIX_WEIGHT,
                CliSyntax.PREFIX_MEDICALCONDITIONS);

        if (!arePrefixesPresent(argMultimap,
            CliSyntax.PREFIX_NAME,
            CliSyntax.PREFIX_PHONE,
            CliSyntax.PREFIX_AGE,
            CliSyntax.PREFIX_BLOODTYPE,
            CliSyntax.PREFIX_GENDER,
            CliSyntax.PREFIX_HEIGHT,
            CliSyntax.PREFIX_WEIGHT)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPatientCommand.MESSAGE_USAGE
                ));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get());
        Age age = ParserUtil.parseAge(argMultimap.getValue(CliSyntax.PREFIX_AGE).get());
        BloodType bloodType = ParserUtil.parseBloodType(argMultimap.getValue(CliSyntax.PREFIX_BLOODTYPE).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(CliSyntax.PREFIX_GENDER).get());
        Height height = ParserUtil.parseHeight(argMultimap.getValue(CliSyntax.PREFIX_HEIGHT).get());
        Weight weight = ParserUtil.parseWeight(argMultimap.getValue(CliSyntax.PREFIX_WEIGHT).get());
        Set<Tag> medicalConditions = ParserUtil.parseMedicalConditions(argMultimap
            .getAllValues(CliSyntax.PREFIX_MEDICALCONDITIONS));

        return new AddPatientCommand(name, phone, age, bloodType, gender, height, weight, medicalConditions);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
