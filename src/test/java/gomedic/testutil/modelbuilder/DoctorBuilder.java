package gomedic.testutil.modelbuilder;

import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;

public class DoctorBuilder {
    /**
     * A utility class to help with building Doctor objects.
     */

    public static final String DEFAULT_NAME = "John Doe";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_DEPARTMENT = "Pediatrics";
    private static int idPool = 1;

    private Name name;
    private Phone phone;
    private Department department;
    private DoctorId did;

    /**
     * Creates a {@code DoctorBuilder} with the default details.
     */
    public DoctorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        department = new Department(DEFAULT_DEPARTMENT);
        did = new DoctorId(idPool++);
    }

    /**
     * Initializes the DoctorBuilder with the data of {@code doctorToCopy}.
     */
    public DoctorBuilder(Doctor doctorToCopy) {
        name = doctorToCopy.getName();
        phone = doctorToCopy.getPhone();
        department = doctorToCopy.getDepartment();
        did = (DoctorId) doctorToCopy.getId();
    }

    /**
     * Sets the {@code Name} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Doctor} that we are building.
     */

    public DoctorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Department} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withDepartment(String department) {
        this.department = new Department(department);
        return this;
    }

    /**
     * Sets the {@code DoctorId} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withId(int id) {
        this.did = new DoctorId(id);
        return this;
    }

    public Doctor build() {
        return new Doctor(name, phone, did, department);
    }

}
