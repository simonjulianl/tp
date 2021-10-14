package gomedic.model.person.doctor;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;

public class DoctorTest {
    private static Doctor doctor;
    private static DoctorId id;
    private static Name name;
    private static Phone phone;
    private static Department department;

    @BeforeAll
    public static void setUp() {
        id = new DoctorId(1);
        name = new Name("John Doe version 2");
        phone = new Phone("995");
        department = new Department("Dermatology");

        doctor = new Doctor(name, phone, id, department);
    }

    @Test
    void constructor_anyNull_throwsNullArgumentException() {
        assertThrows(NullPointerException.class, () -> new Doctor(null, null, null, null));
    }

    @Test
    void getDoctorId() {
        assertEquals(id, doctor.getId()); // getId is an inherited method
    }

    @Test
    void getName() {
        assertEquals(name, doctor.getName());
    }

    @Test
    void getPhone() {
        assertEquals(phone, doctor.getPhone());
    }

    @Test
    void getDepartment() {
        assertEquals(department, doctor.getDepartment());
    }

    @Test
    void testHashCode() {
        int hash = Objects.hash(name, phone, id, department);
        assertEquals(hash, doctor.hashCode());
    }

    @Test
    void testEquals() {
        DoctorId diffId = new DoctorId(420);
        Name diffName = new Name("Dohn Joe version 1");
        Phone diffPhone = new Phone("99999999");
        Department diffDepartment = new Department("ENT");

        Doctor doctorDiffId = new Doctor(name, phone, diffId, department);
        Doctor doctorDiffName = new Doctor(diffName, phone, id, department);
        Doctor doctorDiffPhone = new Doctor(name, diffPhone, id, department);
        Doctor doctorDiffDepartment = new Doctor(name, phone, id, diffDepartment);
        Doctor doctorAllSameFields = new Doctor(name, phone, id, department);

        assertNotEquals(doctor, doctorDiffId); // Doctors are different if their ids are different
        assertEquals(doctor, doctorDiffName); // Doctors with different names are the same as long as id is same
        assertEquals(doctor, doctorDiffPhone); // Doctors with different phone numbers are the same if id is same
        assertEquals(doctor, doctorDiffDepartment); // Doctors with different departments are the same if id is same

        // Same if other doctor is a different instance but all identity and data fields are the same
        assertEquals(doctor, doctorAllSameFields);
    }

    @Test
    void testToString() {
        assertEquals("Id: D001; Name: John Doe version 2;"
                + " Phone: 995; "
                + "Department: Dermatology", doctor.toString());
    }
}
