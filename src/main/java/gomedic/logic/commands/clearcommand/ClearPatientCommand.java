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
import gomedic.model.person.patient.Patient;
import javafx.collections.ObservableList;

/**
 * Clears the address book of patients and associated activities.
 */
public class ClearPatientCommand extends Command {

    public static final String COMMAND_WORD = "clear" + " " + PREFIX_TYPE_PATIENT;
    public static final String MESSAGE_SUCCESS = "patients in GoMedic has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Patient> listOfPatients = model.getFilteredPatientList();
        for (Patient patient : listOfPatients) {
            model.deletePatientAssociatedAppointments(patient);
        }
        AddressBook newAddressBook = new AddressBook();
        ReadOnlyAddressBook oldAddressBook = model.getAddressBook();
        newAddressBook.setUserProfile(oldAddressBook.getUserProfile());
        newAddressBook.setActivities(oldAddressBook.getActivityListSortedById());
        newAddressBook.setDoctors(oldAddressBook.getDoctorListSortedById());
        model.setAddressBook(newAddressBook);
        model.setModelBeingShown(ModelItem.PATIENT);
        model.setViewPatient(null);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
