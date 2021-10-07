package gomedic.model.person.doctor;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void isSamePerson_differentDepartment_returnsTrue() {
        Department diffDepartment = new Department("ENT");
        Doctor doctorDiffDepartment = new Doctor(name, phone, id, diffDepartment);

        // Test if doctors with the same name and phone number but different department are the same,
        // since it is possible for doctors to change their departments
        assertTrue(doctor.isSamePerson(doctorDiffDepartment));
    }

    @Test
    void isSamePerson_differentId_returnsFalse() {
        DoctorId diffId = new DoctorId(420);
        Doctor doctorDiffId = new Doctor(name, phone, diffId, department);

        // Test if doctors different ids are different people.
        // isSamePerson is a weak equality comparison; We take the id
        // to assert this weaker equality (similar to the Javascript concept of abstract equality)
        assertFalse(doctor.isSamePerson(doctorDiffId));
    }

    @Test
    void isSamePerson_differentName_returnsTrue() {
        Name diffName = new Name("Dohn Joe version 1");
        Doctor doctorDiffName = new Doctor(diffName, phone, id, department);

        // Doctors with the same id are considered the same person
        assertTrue(doctor.isSamePerson(doctorDiffName));
    }

    @Test
    void isSamePerson_differentPhone_returnsTrue() {
        Phone diffPhone = new Phone("99999999");
        Doctor doctorDiffPhone = new Doctor(name, diffPhone, id, department);

        // Doctors with the same id are considered the same person
        assertTrue(doctor.isSamePerson(doctorDiffPhone));
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

        // Not the same if any of the identity fields are different
        assertNotEquals(doctor, doctorDiffId);
        assertNotEquals(doctor, doctorDiffName);
        assertNotEquals(doctor, doctorDiffPhone);

        // Not the same if any of the data fields are different
        assertNotEquals(doctor, doctorDiffDepartment);

        // Same if other doctor is a different instance but all identity and data fields are the same
        assertEquals(doctor, doctorAllSameFields);
    }

    @Test
    void testToString() {
        assertEquals("John Doe version 2;"
                + " Phone: 995; "
                + "Department: Dermatology", doctor.toString());
    }
}
