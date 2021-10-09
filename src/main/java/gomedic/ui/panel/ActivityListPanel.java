package gomedic.ui.panel;

import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;
import gomedic.model.activity.Activity;
import gomedic.ui.card.ActivityCard;
import gomedic.ui.card.PersonCard;
import gomedic.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class ActivityListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);

    @FXML
    private ListView<Activity> activityListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ActivityListPanel(ObservableList<Activity> activityList) {
        super(FXML);
        activityListView.setItems(activityList);
        activityListView.setCellFactory(listView -> new ActivityListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Activity} using a {@code ActivityCard}.
     */
    static class ActivityListViewCell extends ListCell<Activity> {
        @Override
        protected void updateItem(Activity activity, boolean empty) {
            super.updateItem(activity, empty);

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ActivityCard(activity, getIndex() + 1).getRoot());
            }
        }
    }

}
