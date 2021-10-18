package gomedic.ui.table;

import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;
import gomedic.model.person.patient.Patient;
import gomedic.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of patients.
 */
public class PatientTable extends UiPart<Region> {
    private static final String FXML = "PatientTable.fxml";
    private final Logger logger = LogsCenter.getLogger(PatientTable.class);

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, String> idField;

    @FXML
    private TableColumn<Patient, String> nameField;

    @FXML
    private TableColumn<Patient, String> contactField;

    @FXML
    private TableColumn<Patient, String> ageField;

    @FXML
    private TableColumn<Patient, String> genderField;

    /**
     * Creates a {@code PatientTable} with the given {@code ObservableList}.
     */
    public PatientTable(ObservableList<Patient> patientList) {
        super(FXML);
        patientTable.setItems(patientList);
        patientTable.setPlaceholder(new Label("No patients to display"));

        // sort the id field in ascending order by default.
        idField.setSortType(TableColumn.SortType.ASCENDING);
        idField.setSortable(true);
    }
}
