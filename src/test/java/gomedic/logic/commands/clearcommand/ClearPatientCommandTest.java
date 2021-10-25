package gomedic.logic.commands.clearcommand;

import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import gomedic.model.AddressBook;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.UserPrefs;
import gomedic.model.activity.Activity;
import gomedic.testutil.modelbuilder.ActivityBuilder;
import javafx.collections.ObservableList;

public class ClearPatientCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearPatientCommand(), model, ClearPatientCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        AddressBook newAddressBook = new AddressBook();
        ReadOnlyAddressBook oldAddressBook = model.getAddressBook();
        newAddressBook.setDoctors(oldAddressBook.getDoctorListSortedById());
        Activity activityWithMatchingPatient = new ActivityBuilder()
                .withId(5)
                .withPatientId(1)
                .withTitle("Meeting me")
                .withDescription("today at somewhere")
                .withStartTime("20/09/2022 13:00")
                .withEndTime("21/09/2022 15:00")
                .build();
        ObservableList<Activity> remainingActivities = oldAddressBook.getActivityListSortedById()
                .filtered(x -> !x.equals(activityWithMatchingPatient));
        newAddressBook.setActivities(remainingActivities);

        expectedModel.setAddressBook(newAddressBook);

        assertCommandSuccess(new ClearPatientCommand(), model, ClearPatientCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
