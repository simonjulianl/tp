package gomedic.logic.commands.addcommand;

import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.activity.Activity;
import gomedic.testutil.modelbuilder.ActivityBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddActivityIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newActivity_success() {
        Activity validActivity = new ActivityBuilder()
                .withId(model.getNewActivityId())
                .withStartTime("15/07/2000 15:00")
                .withEndTime("15/07/2000 16:00")
                .withTitle("testing")
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addActivity(validActivity);

        CommandTestUtil.assertCommandSuccess(
                new AddActivityCommand(
                        validActivity.getStartTime(),
                        validActivity.getEndTime(),
                        validActivity.getTitle(),
                        validActivity.getDescription()),
                model,
                String.format(AddActivityCommand.MESSAGE_SUCCESS,
                        validActivity),
                expectedModel);
    }

    @Test
    public void execute_conflictingActivity_throwsCommandException() {
        Activity activityInList = model.getAddressBook().getActivityListSortedById().get(0);
        CommandTestUtil.assertCommandFailure(
                new AddActivityCommand(
                        activityInList.getStartTime(),
                        activityInList.getEndTime(),
                        activityInList.getTitle(),
                        activityInList.getDescription()
                ),
                model,
                AddActivityCommand.MESSAGE_CONFLICTING_ACTIVITY);
    }
}
