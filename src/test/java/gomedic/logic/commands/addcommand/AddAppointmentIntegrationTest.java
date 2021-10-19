package gomedic.logic.commands.addcommand;

import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.activity.Activity;
import gomedic.testutil.modelbuilder.ActivityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddAppointmentIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newActivity_success() {
        Activity validAppointment = new ActivityBuilder()
                .withPatientId(1)
                .withId(model.getNewActivityId())
                .withStartTime("15/07/2000 15:00")
                .withEndTime("15/07/2000 16:00")
                .withTitle("testing")
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addActivity(validAppointment);

        CommandTestUtil.assertCommandSuccess(
                new AddAppointmentCommand(
                        validAppointment.getPatientId(),
                        validAppointment.getStartTime(),
                        validAppointment.getEndTime(),
                        validAppointment.getTitle(),
                        validAppointment.getDescription()),
                model,
                String.format(AddAppointmentCommand.MESSAGE_SUCCESS,
                        validAppointment),
                expectedModel);
    }
}
