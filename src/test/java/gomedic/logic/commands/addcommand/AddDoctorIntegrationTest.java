package gomedic.logic.commands.addcommand;

import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.person.doctor.Doctor;
import gomedic.testutil.modelbuilder.DoctorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddDoctorIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newDoctor_success() {
        Doctor validDoctor = new DoctorBuilder()
                .withId(model.getNewDoctorId())
                .withName("a new name here")
                .withPhone("12309811")
                .withDepartment("testing")
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addDoctor(validDoctor);

        CommandTestUtil.assertCommandSuccess(
                new AddDoctorCommand(
                        validDoctor.getName(),
                        validDoctor.getPhone(),
                        validDoctor.getDepartment()),
                model,
                String.format(AddDoctorCommand.MESSAGE_SUCCESS,
                        validDoctor),
                expectedModel);
    }
}
