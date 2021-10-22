package gomedic.logic.commands.findcommand;

import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.testutil.TypicalPersons.MAIN_PATIENT;
import static gomedic.testutil.TypicalPersons.OTHER_PATIENT;
import static gomedic.testutil.TypicalPersons.THIRD_PATIENT;
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
import gomedic.model.person.patient.Patient;
import gomedic.model.util.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPatientCommand}.
 */
public class FindPatientCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate<Patient> firstPredicate =
                new NameContainsKeywordsPredicate<>(Collections.singletonList("first"));
        NameContainsKeywordsPredicate<Patient> secondPredicate =
                new NameContainsKeywordsPredicate<>(Collections.singletonList("second"));

        FindPatientCommand findFirstCommand = new FindPatientCommand(firstPredicate);
        FindPatientCommand findSecondCommand = new FindPatientCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindPatientCommand findFirstCommandCopy = new FindPatientCommand(firstPredicate);
        assertEquals(findFirstCommandCopy, findFirstCommand);

        // different types -> returns false
        assertNotEquals(findFirstCommand, 1);

        // null -> returns false
        assertNotEquals(findFirstCommand, null);

        // different person -> returns false
        assertNotEquals(findSecondCommand, findFirstCommand);
    }

    @Test
    public void execute_zeroKeywords_noPatientFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate<Patient> predicate = preparePredicate("THISKEYWORDCANTMATCHANYTHING");
        FindPatientCommand command = new FindPatientCommand(predicate);
        expectedModel.updateFilteredPatientList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPatientList());
    }

    @Test
    public void execute_multipleKeywords_multiplePatientsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, 3);
        //  predicate keywords based on the typical patients list
        NameContainsKeywordsPredicate<Patient> predicate = preparePredicate("John Smith");
        FindPatientCommand command = new FindPatientCommand(predicate);
        expectedModel.updateFilteredPatientList(predicate);
        //assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MAIN_PATIENT, OTHER_PATIENT, THIRD_PATIENT), model.getFilteredPatientList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate<Patient> preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate<Patient>(Arrays.asList(userInput.split("\\s+")));
    }
}
