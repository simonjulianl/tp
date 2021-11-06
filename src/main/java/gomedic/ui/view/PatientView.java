package gomedic.ui.view;

import java.util.Comparator;
import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;
import gomedic.model.activity.Activity;
import gomedic.model.person.patient.Patient;
import gomedic.ui.UiPart;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class PatientView extends UiPart<Region> {
    private static final String FXML = "PatientView.fxml";
    private static final Logger logger = LogsCenter.getLogger(PatientView.class);
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Patient patient;

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
     * Creates a {@code PatientCode} with the given {@code ObservableValue<Patient>} and
     * {@code ObservableList<Activity>} to display.
     */
    public PatientView(ObservableValue<Patient> object, ObservableList<Activity> activityList) {
        super(FXML);
        patient = object.getValue();
        object.addListener((add, oldVal, newVal) -> {
            try {
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

                medicalConditions.setPrefWrapLength(300f);
                medicalConditions.setPrefHeight(0f);
                medicalConditions.setMaxWidth(300f);
            } catch (Exception e) {
                return;
            }

            logger.info("Getting associated appointments");

            try {
                activityList.stream()
                        .filter(activity -> newVal.getId().equals(activity.getPatientId()))
                        .forEach(activity -> appointments
                                .getChildren()
                                .add(new Label("start : " + activity.getStartTime().toString() + "\nend  : "
                                    + activity.getEndTime().toString())));
            } catch (Exception e) {
                logger.severe("Unable to fetch associated appointments");
            }
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
