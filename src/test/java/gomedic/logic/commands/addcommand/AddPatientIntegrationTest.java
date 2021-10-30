package gomedic.logic.commands.addcommand;

import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.person.patient.Patient;
import gomedic.model.tag.Tag;
import gomedic.testutil.modelbuilder.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddPatientIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPatient_success() {
        Patient validPatient = new PatientBuilder()
            .withId(model.getNewPatientId())
            .withName("a new name here")
            .withPhone("12309811")
            .withAge("40")
            .withGender("M")
            .withBloodType("B+")
            .withHeight("175")
            .withWeight("65")
            .withMedicalConditions(new HashSet<Tag>())
            .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPatient(validPatient);

        CommandTestUtil.assertCommandSuccess(
            new AddPatientCommand(
                validPatient.getName(),
                validPatient.getPhone(),
                validPatient.getAge(),
                validPatient.getBloodType(),
                validPatient.getGender(),
                validPatient.getHeight(),
                validPatient.getWeight(),
                validPatient.getMedicalConditions()),
            model,
            String.format(AddPatientCommand.MESSAGE_SUCCESS,
                validPatient),
            expectedModel);
    }
}
