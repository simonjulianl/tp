package gomedic.ui;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Controller for the side window.
 */
public class SideWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(SideWindow.class);
    private static final String FXML = "SideWindow.fxml";
    private static final String ACTIVITY_LOGO_URL = "/images/activityicon";
    private static final String DOCTOR_LOGO_URL = "/images/doctoricon";
    private static final String PATIENT_LOGO_URL = "/images/patienticon";
    private static final String IMAGE_TYPE = ".png";
    private static final String ACTIVE_SUFFIX = "active";

    @FXML
    private final HashMap<String, Image> imageHashMap;
    @FXML
    private Label myName;
    @FXML
    private Label myDepartment;
    @FXML
    private Label activitylabel;
    @FXML
    private ImageView displayPictureActivity;
    @FXML
    private Label patientLabel;
    @FXML
    private ImageView displayPicturePatient;
    @FXML
    private Label doctorLabel;
    @FXML
    private ImageView displayPictureDoctor;

    {
        imageHashMap = new HashMap<>();
        String[] logoUrls = {ACTIVITY_LOGO_URL, PATIENT_LOGO_URL, DOCTOR_LOGO_URL};

        for (String logoUrl : logoUrls) {
            Image logoImageNormal = new Image(Objects.requireNonNull(this.getClass()
                    .getResourceAsStream(logoUrl + IMAGE_TYPE)));
            imageHashMap.put(logoUrl, logoImageNormal);

            Image logoImageActive = new Image(Objects.requireNonNull(this.getClass()
                    .getResourceAsStream(logoUrl + ACTIVE_SUFFIX + IMAGE_TYPE)));
            imageHashMap.put(logoUrl + ACTIVE_SUFFIX, logoImageActive);
        }
    }

    /**
     * Creates a new SideWindow.
     */
    public SideWindow() {
        super(FXML);

        // set up the pictures
        displayPictureActivity.setImage(imageHashMap.get(ACTIVITY_LOGO_URL));
        displayPicturePatient.setImage(imageHashMap.get(PATIENT_LOGO_URL));
        displayPictureDoctor.setImage(imageHashMap.get(DOCTOR_LOGO_URL));
    }
}
