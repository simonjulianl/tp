package gomedic.ui.table;

import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;
import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Time;
import gomedic.model.person.patient.PatientId;
import gomedic.ui.UiPart;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of activities.
 */
public class ActivityTable extends UiPart<Region> {
    private static final String FXML = "ActivityTable.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityTable.class);

    @FXML
    private TableView<Activity> activityTable;

    @FXML
    private TableColumn<Activity, ActivityId> idField;

    @FXML
    private TableColumn<Activity, Title> titleField;

    @FXML
    private TableColumn<Activity, PatientId> patientField;

    @FXML
    private TableColumn<Activity, Time> startTimeField;

    @FXML
    private TableColumn<Activity, Time> endTimeField;

    @FXML
    private TableColumn<Activity, Description> descriptionField;

    /**
     * Creates a {@code ActivityTable} with the given {@code ObservableList}.
     */
    public ActivityTable(ObservableList<Activity> activityList) {
        super(FXML);
        logger.info("Setting up the activity table");

        activityList.addListener((ListChangeListener) change -> activityTable.refresh());

        activityTable.setItems(activityList);
        activityTable.setPlaceholder(new Label("No activities to display"));

        // sort the id field in ascending order by default.
        idField.setSortType(TableColumn.SortType.ASCENDING);
        idField.setSortable(true);

        // make the startTime and endTime sortable
        startTimeField.setSortType(TableColumn.SortType.ASCENDING);
        startTimeField.setSortable(true);
        startTimeField.widthProperty().addListener((obs, oldVal, newVal) -> {
            TableUtil.setWrapTextColumn(startTimeField, newVal);
        });

        endTimeField.setSortType(TableColumn.SortType.ASCENDING);
        endTimeField.setSortable(true);
        endTimeField.widthProperty().addListener((obs, oldVal, newVal) -> {
            TableUtil.setWrapTextColumn(endTimeField, newVal);
        });

        titleField.widthProperty().addListener((obs, oldVal, newVal) -> {
            TableUtil.setWrapTextColumn(titleField, newVal);
        });

        descriptionField.widthProperty().addListener((obs, oldVal, newVal) -> {
            TableUtil.setWrapTextColumn(descriptionField, newVal);
        });
    }
}
