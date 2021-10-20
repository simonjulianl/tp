package gomedic.logic.commands.clearcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_PATIENT;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.model.AddressBook;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.ReadOnlyAddressBook;

/**
 * Clears the address book.
 */
public class ClearPatientCommand extends Command {

    public static final String COMMAND_WORD = "clear" + " " + PREFIX_TYPE_PATIENT;
    public static final String MESSAGE_SUCCESS = "patients in GoMedic has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        AddressBook newAddressBook = new AddressBook();
        ReadOnlyAddressBook oldAddressBook = model.getAddressBook();
        newAddressBook.setActivities(oldAddressBook.getActivityListSortedById());
        newAddressBook.setDoctors(oldAddressBook.getDoctorListSortedById());
        model.setAddressBook(newAddressBook);
        model.setModelBeingShown(ModelItem.PATIENT);
        model.viewPatient(null);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
