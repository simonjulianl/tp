package gomedic.logic.parser.addcommandparser;

import java.util.Set;
import java.util.stream.Stream;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.addcommand.AddPersonCommand;
import gomedic.logic.parser.ArgumentMultimap;
import gomedic.logic.parser.ArgumentTokenizer;
import gomedic.logic.parser.CliSyntax;
import gomedic.logic.parser.Parser;
import gomedic.logic.parser.ParserUtil;
import gomedic.logic.parser.Prefix;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Address;
import gomedic.model.commonfield.Email;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.Person;
import gomedic.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddPersonCommand object
 */
public class AddPersonCommandParser implements Parser<AddPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonCommand
     * and returns an AddPersonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        CliSyntax.PREFIX_NAME,
                        CliSyntax.PREFIX_PHONE,
                        CliSyntax.PREFIX_EMAIL,
                        CliSyntax.PREFIX_ADDRESS,
                        CliSyntax.PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap,
                CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_ADDRESS,
                CliSyntax.PREFIX_PHONE,
                CliSyntax.PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(
                            Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            AddPersonCommand.MESSAGE_USAGE
                    ));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(CliSyntax.PREFIX_TAG));

        Person person = new Person(name, phone, email, address, tagList);

        return new AddPersonCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
