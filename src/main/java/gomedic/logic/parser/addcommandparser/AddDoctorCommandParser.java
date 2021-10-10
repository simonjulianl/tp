package gomedic.logic.parser.addcommandparser;

import java.util.stream.Stream;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.addcommand.AddDoctorCommand;
import gomedic.logic.parser.ArgumentMultimap;
import gomedic.logic.parser.ArgumentTokenizer;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.Prefix;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;

/**
 * Parses input arguments and creates a new AddDoctorCommand object.
 */
public class AddDoctorCommandParser implements Parser<AddDoctorCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddDoctorCommand
     * and returns an AddDoctorCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDoctorCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        CliSyntax.PREFIX_NAME,
                        CliSyntax.PREFIX_PHONE,
                        CliSyntax.PREFIX_DEPARTMENT);

        if (!arePrefixesPresent(argMultimap,
                CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_PHONE,
                CliSyntax.PREFIX_DEPARTMENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(
                            Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            AddDoctorCommand.MESSAGE_USAGE
                    ));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get());
        Department department = ParserUtil.parseDepartment(argMultimap.getValue(CliSyntax.PREFIX_DEPARTMENT).get());

        return new AddDoctorCommand(name, phone, department);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
