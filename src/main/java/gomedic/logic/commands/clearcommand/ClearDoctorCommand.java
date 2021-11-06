package gomedic.logic.commands.clearcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_DOCTOR;
import static java.util.Objects.requireNonNull;

import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.model.AddressBook;
import gomedic.model.Model;
import gomedic.model.ReadOnlyAddressBook;

/**
 * Clears the address book.
 */
public class ClearDoctorCommand extends Command {

    public static final String COMMAND_WORD = "clear" + " " + PREFIX_TYPE_DOCTOR;
    public static final String MESSAGE_SUCCESS = "doctors in GoMedic has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        AddressBook newAddressBook = new AddressBook();
        ReadOnlyAddressBook oldAddressBook = model.getAddressBook();
        newAddressBook.setUserProfile(oldAddressBook.getUserProfile());
        newAddressBook.setActivities(oldAddressBook.getActivityListSortedById());
        newAddressBook.setPatients(oldAddressBook.getPatientListSortedById());
        model.setAddressBook(newAddressBook);
        model.setViewPatient(null);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
