package gomedic.ui.table;

import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;
import gomedic.model.person.doctor.Doctor;
import gomedic.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of doctors.
 */
public class DoctorTable extends UiPart<Region> {
    private static final String FXML = "DoctorTable.fxml";
    private final Logger logger = LogsCenter.getLogger(DoctorTable.class);

    @FXML
    private TableView<Doctor> doctorTable;

    @FXML
    private TableColumn<Doctor, String> idField;

    @FXML
    private TableColumn<Doctor, String> nameField;

    @FXML
    private TableColumn<Doctor, String> contactField;

    @FXML
    private TableColumn<Doctor, String> departmentField;

    /**
     * Creates a {@code DoctorTable} with the given {@code ObservableList}.
     */
    public DoctorTable(ObservableList<Doctor> doctorList) {
        super(FXML);
        doctorTable.setItems(doctorList);
        doctorTable.setPlaceholder(new Label("No doctors to display"));

        // sort the id field in ascending order by default.
        idField.setSortType(TableColumn.SortType.ASCENDING);
        idField.setSortable(true);
    }
}
