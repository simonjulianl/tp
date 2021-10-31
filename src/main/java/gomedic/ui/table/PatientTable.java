package gomedic.ui.table;

import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.patient.Age;
import gomedic.model.person.patient.Gender;
import gomedic.model.person.patient.Patient;
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
 * Panel containing the list of patients.
 */
public class PatientTable extends UiPart<Region> {
    private static final String FXML = "PatientTable.fxml";

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, PatientId> idField;

    @FXML
    private TableColumn<Patient, Name> nameField;

    @FXML
    private TableColumn<Patient, Phone> contactField;

    @FXML
    private TableColumn<Patient, Age> ageField;

    @FXML
    private TableColumn<Patient, Gender> genderField;

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

        patientList.addListener((ListChangeListener) change -> patientTable.refresh());

        nameField.widthProperty().addListener((obs, oldVal, newVal) -> {
            TableUtil.setWrapTextColumn(nameField, newVal);
        });
    }
}
