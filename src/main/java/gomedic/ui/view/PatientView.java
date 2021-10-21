package gomedic.ui.view;

import java.util.Comparator;

import gomedic.model.person.patient.Patient;
import gomedic.ui.UiPart;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class PatientView extends UiPart<Region> {
    private static final String FXML = "PatientView.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Patient patient;

//    @FXML
//    private StackPane cardPane;
    @FXML
    private GridPane patientGrid;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label age;
    @FXML
    private Label bloodType;
    @FXML
    private Label gender;
    @FXML
    private Label height;
    @FXML
    private Label weight;
    @FXML
    private FlowPane medicalConditions;
    @FXML
    private FlowPane appointments;

    /**
     * Creates a {@code PatientCode} with the given {@code ObservableValue<Patient>} and index to display.
     */
    public PatientView(ObservableValue<Patient> object) {
        super(FXML);
        patient = object.getValue();
        object.addListener((add, oldVal, newVal) -> {
            id.setText(newVal.getId().toString());
            name.setText(newVal.getName().toString());
            phone.setText(newVal.getPhone().toString());
            age.setText(newVal.getAge().toString());
            bloodType.setText(newVal.getBloodType().toString());
            gender.setText(newVal.getGender().toString());
            height.setText(newVal.getHeight().toString());
            weight.setText(newVal.getWeight().toString());
            medicalConditions.getChildren().clear();
            newVal.getMedicalConditions().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> medicalConditions.getChildren().add(new Label(tag.tagName)));
            appointments.getChildren().clear();
            newVal.getMedicalConditions().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> appointments.getChildren().add(new Label(tag.tagName)));
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PatientView)) {
            return false;
        }

        // state check
        PatientView card = (PatientView) other;
        return patient.equals(card.patient);
    }
}
