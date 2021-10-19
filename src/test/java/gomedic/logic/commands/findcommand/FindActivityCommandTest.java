package gomedic.logic.commands.findcommand;

import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import gomedic.commons.core.Messages;
import gomedic.model.activity.Activity;
import gomedic.model.util.ActivityTitleContainsKeywordsPredicate;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code FindActivityCommand}.
 */
public class FindActivityCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ActivityTitleContainsKeywordsPredicate<Activity> firstPredicate =
                new ActivityTitleContainsKeywordsPredicate<Activity>(Collections.singletonList("first"));
        ActivityTitleContainsKeywordsPredicate<Activity> secondPredicate =
                new ActivityTitleContainsKeywordsPredicate<Activity>(Collections.singletonList("second"));

        FindActivityCommand findFirstCommand = new FindActivityCommand(firstPredicate);
        FindActivityCommand findSecondCommand = new FindActivityCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindActivityCommand findFirstCommandCopy = new FindActivityCommand(firstPredicate);
        assertEquals(findFirstCommandCopy, findFirstCommand);

        // different types -> returns false
        assertNotEquals(findFirstCommand, 1);

        // null -> returns false
        assertNotEquals(findFirstCommand, null);

        // different person -> returns false
        assertNotEquals(findSecondCommand, findFirstCommand);
    }

    @Test
    public void execute_zeroKeywords_noActivityFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        ActivityTitleContainsKeywordsPredicate<Activity> predicate = preparePredicate("THISKEYWORDCANTMATCHANYTHING");
        FindActivityCommand command = new FindActivityCommand(predicate);
        expectedModel.updateFilteredActivitiesList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredActivityListById());
    }

    @Test
    public void execute_multipleKeywords_multipleActivitiesFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, 3);
        //  predicate keywords based on the typical patients list
        ActivityTitleContainsKeywordsPredicate<Activity> predicate = preparePredicate("Meeting");
        FindActivityCommand command = new FindActivityCommand(predicate);
        expectedModel.updateFilteredActivitiesList(predicate);
        //assertCommandSuccess(command, model, expectedMessage, expectedModel);
        //assertEquals(Arrays.asList(MEETING, CONFLICTING_MEETING), model.getFilteredActivityListById());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private ActivityTitleContainsKeywordsPredicate<Activity> preparePredicate(String userInput) {
        return new ActivityTitleContainsKeywordsPredicate<Activity>(Arrays.asList(userInput.split("\\s+")));
    }

}
