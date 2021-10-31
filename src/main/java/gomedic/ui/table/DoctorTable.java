package gomedic.ui.table;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;
import gomedic.ui.UiPart;
import javafx.collections.ListChangeListener;
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

    @FXML
    private TableView<Doctor> doctorTable;

    @FXML
    private TableColumn<Doctor, DoctorId> idField;

    @FXML
    private TableColumn<Doctor, Name> nameField;

    @FXML
    private TableColumn<Doctor, Phone> contactField;

    @FXML
    private TableColumn<Doctor, Department> departmentField;

    /**
     * Creates a {@code DoctorTable} with the given {@code ObservableList}.
     */
    public DoctorTable(ObservableList<Doctor> doctorList) {
        super(FXML);

        doctorList.addListener((ListChangeListener) change -> doctorTable.refresh());

        doctorTable.setItems(doctorList);
        doctorTable.setPlaceholder(new Label("No doctors to display"));

        // sort the id field in ascending order by default.
        idField.setSortType(TableColumn.SortType.ASCENDING);
        idField.setSortable(true);

        nameField.widthProperty().addListener((obs, oldVal, newVal) -> {
            TableUtil.setWrapTextColumn(nameField, newVal);
        });

        departmentField.widthProperty().addListener((obs, oldVal, newVal) -> {
            TableUtil.setWrapTextColumn(departmentField, newVal);
        });
    }
}
