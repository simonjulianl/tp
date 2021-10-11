package gomedic.ui.panel;

import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;
import gomedic.model.person.doctor.Doctor;
import gomedic.ui.UiPart;
import gomedic.ui.card.DoctorCard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of doctors.
 */
public class DoctorListPanel extends UiPart<Region> {
    private static final String FXML = "DoctorListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DoctorListPanel.class);

    @FXML
    private ListView<Doctor> doctorListView;

    /**
     * Creates a {@code DoctorListPanel} with the given {@code ObservableList}.
     */
    public DoctorListPanel(ObservableList<Doctor> doctorList) {
        super(FXML);
        doctorListView.setItems(doctorList);
        doctorListView.setCellFactory(listView -> new DoctorListPanel.DoctorListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Doctor} using a {@code DoctorCard}.
     */
    static class DoctorListViewCell extends ListCell<Doctor> {
        @Override
        protected void updateItem(Doctor doctor, boolean empty) {
            super.updateItem(doctor, empty);

            if (empty || doctor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DoctorCard(doctor).getRoot());
            }
        }
    }
}
