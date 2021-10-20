package gomedic.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import gomedic.model.userprofile.UserProfile;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label userName;

    @FXML
    private Label userDescription;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation, ObservableValue<UserProfile> userProfile) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        userName.setText(userProfile.getValue().getName().fullName);
        userDescription.setText(userProfile.getValue().getDescription().toString());
        userProfile.addListener((add, oldVal, newVal) -> {
            userName.setText(newVal.getName().fullName);
            userDescription.setText(newVal.getDescription().toString());
        });

    }

}
