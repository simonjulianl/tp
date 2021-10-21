package gomedic.ui.card;

import java.util.Comparator;

import gomedic.model.person.patient.Patient;
import gomedic.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class PatientCard extends UiPart<Region> {
    private static final String FXML = "PatientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Patient patient;

    @FXML
    private HBox cardPane;
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

    /**
     * Creates a {@code PatientCode} with the given {@code Patient} and index to display.
     */
    public PatientCard(Patient patient) {
        super(FXML);
        this.patient = patient;
        id.setText(patient.getId().toString() + ".");
        name.setText(patient.getName().toString());
        phone.setText(patient.getPhone().toString());
        age.setText(patient.getAge().toString());
        bloodType.setText(patient.getBloodType().toString());
        gender.setText(patient.getGender().toString());
        height.setText(patient.getHeight().toString());
        weight.setText(patient.getWeight().toString());
        patient.getMedicalConditions().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> medicalConditions.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PatientCard)) {
            return false;
        }

        // state check
        PatientCard card = (PatientCard) other;
        return patient.equals(card.patient);
    }
}
