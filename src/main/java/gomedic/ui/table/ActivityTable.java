package gomedic.ui.table;

import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;
import gomedic.model.activity.Activity;
import gomedic.ui.UiPart;
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
    private TableColumn<Activity, String> idField;

    @FXML
    private TableColumn<Activity, String> titleField;

    @FXML
    private TableColumn<Activity, String> period;

    @FXML
    private TableColumn<Activity, String> startTimeField;

    @FXML
    private TableColumn<Activity, String> endTimeField;

    @FXML
    private TableColumn<Activity, String> descriptionField;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ActivityTable(ObservableList<Activity> activityList) {
        super(FXML);
        activityTable.setItems(activityList);
        activityTable.setPlaceholder(new Label("No activities to display"));

        // sort the id field in ascending order by default.
        idField.setSortType(TableColumn.SortType.ASCENDING);
        idField.setSortable(true);

        // make the startTime and endTime sortable
        startTimeField.setSortType(TableColumn.SortType.ASCENDING); // TODO : Write the custom sorting comparator
        startTimeField.setSortable(true);

        endTimeField.setSortType(TableColumn.SortType.ASCENDING); // TODO : Write the custom sorting comparator
        endTimeField.setSortable(true);
    }
}
