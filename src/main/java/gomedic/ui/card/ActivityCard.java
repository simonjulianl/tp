package gomedic.ui.card;

import gomedic.model.activity.Activity;
import gomedic.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Activity}.
 */
public class ActivityCard extends UiPart<Region> {

    private static final String FXML = "ActivityListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Activity activity;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label patientId;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label endTime;
    @FXML
    private Label startTime;

    /**
     * Creates a {@code ActivityCode} with the given {@code Activity} and index to display.
     */
    public ActivityCard(Activity activity) {
        super(FXML);
        this.activity = activity;
        if (activity.isAppointment()) {
            String pId = activity.getPatientId().toString();
            patientId.setText(String.format("Appointment with: %s", pId));
        } else {
            patientId.setText("N.A.");
        }
        id.setText(activity.getActivityId().toString() + ".");
        title.setText(activity.getTitle().toString());
        description.setText(activity.getDescription().toString());
        startTime.setText(activity.getStartTime().toString());
        endTime.setText(activity.getEndTime().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityCard)) {
            return false;
        }

        // state check
        ActivityCard card = (ActivityCard) other;
        return activity.equals(card.activity);
    }
}
