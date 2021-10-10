package gomedic.ui.card;

import gomedic.model.person.doctor.Doctor;
import gomedic.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Doctor}.
 */
public class DoctorCard extends UiPart<Region> {
    private static final String FXML = "DoctorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Doctor doctor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label department;

    /**
     * Creates a {@code DoctorCode} with the given {@code Doctor} and index to display.
     */
    public DoctorCard(Doctor doctor) {
        super(FXML);
        this.doctor = doctor;
        id.setText(doctor.getId().toString() + ".");
        name.setText(doctor.getName().toString());
        phone.setText(doctor.getPhone().toString());
        department.setText(doctor.getDepartment().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DoctorCard)) {
            return false;
        }

        // state check
        DoctorCard card = (DoctorCard) other;
        return doctor.equals(card.doctor);
    }
}
