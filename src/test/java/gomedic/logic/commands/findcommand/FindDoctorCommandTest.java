package gomedic.logic.commands.findcommand;

import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.testutil.TypicalPersons.MAIN_DOCTOR;
import static gomedic.testutil.TypicalPersons.OTHER_DOCTOR;
import static gomedic.testutil.TypicalPersons.THIRD_DOCTOR;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.util.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindDoctorCommand}.
 */
public class FindDoctorCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate<Doctor> firstPredicate =
                new NameContainsKeywordsPredicate<>(Collections.singletonList("first"));
        NameContainsKeywordsPredicate<Doctor> secondPredicate =
                new NameContainsKeywordsPredicate<>(Collections.singletonList("second"));

        FindDoctorCommand findFirstCommand = new FindDoctorCommand(firstPredicate);
        FindDoctorCommand findSecondCommand = new FindDoctorCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindDoctorCommand findFirstCommandCopy = new FindDoctorCommand(firstPredicate);
        assertEquals(findFirstCommandCopy, findFirstCommand);

        // different types -> returns false
        assertNotEquals(findFirstCommand, 1);

        // null -> returns false
        assertNotEquals(findFirstCommand, null);

        // different person -> returns false
        assertNotEquals(findSecondCommand, findFirstCommand);
    }

    @Test
    public void execute_zeroKeywords_noDoctorFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate<Doctor> predicate = preparePredicate("THISKEYWORDCANTMATCHANYTHING");
        FindDoctorCommand command = new FindDoctorCommand(predicate);
        expectedModel.updateFilteredDoctorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDoctorList());
    }

    @Test
    public void execute_multipleKeywords_multipleDoctorsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, 3);
        //  predicate keywords based on the typical doctors list
        NameContainsKeywordsPredicate<Doctor> predicate = preparePredicate("John Smith");
        FindDoctorCommand command = new FindDoctorCommand(predicate);
        expectedModel.updateFilteredDoctorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MAIN_DOCTOR, OTHER_DOCTOR, THIRD_DOCTOR), model.getFilteredDoctorList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate<Doctor> preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }
}
