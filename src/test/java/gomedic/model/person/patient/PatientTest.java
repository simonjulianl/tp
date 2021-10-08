package gomedic.model.person.patient;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.tag.Tag;

public class PatientTest {
    private static Patient patient;
    private static PatientId id;
    private static Name name;
    private static Phone phone;
    private static Age age;
    private static BloodType bloodType;
    private static Gender gender;
    private static Height height;
    private static Weight weight;
    private static Set<Tag> medicalConditions;

    @BeforeAll
    public static void setUp() {
        id = new PatientId(1);
        name = new Name("John Doe");
        phone = new Phone("12345678");
        age = new Age("31");
        bloodType = new BloodType("A");
        gender = new Gender("M");
        height = new Height("175");
        weight = new Weight("70");
        medicalConditions = new HashSet<>();
        medicalConditions.add(new Tag("heart failure"));

        patient = new Patient(name, phone, id, age, bloodType, gender, height, weight, medicalConditions);
    }

    @Test
    void constructor_anyNull_throwsNullArgumentException() {
        assertThrows(NullPointerException.class, () -> new Patient(null, null, null, null,
            null, null, null, null, null));
    }

    @Test
    void getPatientId() {
        assertEquals(id, patient.getId()); // getId is an inherited method
    }

    @Test
    void getName() {
        assertEquals(name, patient.getName());
    }

    @Test
    void getPhone() {
        assertEquals(phone, patient.getPhone());
    }

    @Test
    void getAge() {
        assertEquals(age, patient.getAge());
    }

    @Test
    void getBloodType() {
        assertEquals(bloodType, patient.getBloodType());
    }

    @Test
    void getGender() {
        assertEquals(gender, patient.getGender());
    }

    @Test
    void getHeight() {
        assertEquals(height, patient.getHeight());
    }

    @Test
    void getWeight() {
        assertEquals(weight, patient.getWeight());
    }

    @Test
    void getMedicalConditions() {
        assertEquals(medicalConditions, patient.getMedicalConditions());
    }

    @Test
    void testHashCode() {
        int hash = Objects.hash(name, phone, id, age, bloodType, gender, height, weight, medicalConditions);
        assertEquals(hash, patient.hashCode());
    }

    @Test
    void testEquals() {
        PatientId diffId = new PatientId(420);
        Name diffName = new Name("Dohn Joe");
        Phone diffPhone = new Phone("99999999");
        Age diffAge = new Age("30");
        BloodType diffBloodtype = new BloodType("O");
        Gender diffGender = new Gender("O");
        Height diffHeight = new Height("176");
        Weight diffWeight = new Weight("85");
        Set<Tag> diffMedicalConditions = new HashSet<>();
        diffMedicalConditions.add(new Tag("diabetes"));

        Patient patientDiffId = new Patient(name, phone, diffId, age, bloodType, gender, height,
            weight, medicalConditions);
        Patient patientDiffName = new Patient(diffName, phone, id, age, bloodType, gender, height,
            weight, medicalConditions);
        Patient patientDiffPhone = new Patient(name, diffPhone, id, age, bloodType, gender, height,
            weight, medicalConditions);
        Patient patientDiffOthers = new Patient(name, phone, id, age, diffBloodtype, diffGender,
            diffHeight, diffWeight, diffMedicalConditions);
        Patient patientAllSameFields = new Patient(name, phone, id, age, bloodType, gender, height,
            weight, medicalConditions);

        assertNotEquals(patient, patientDiffId); // Patients are different if their ids are different
        assertEquals(patient, patientDiffName); // Patients with different names are the same as long as id is same
        assertEquals(patient, patientDiffPhone); // Patients with different phone numbers are the same if id is same
        assertEquals(patient, patientDiffOthers); // Patients with different details are the same if id is same

        // Same if other patient is a different instance but all identity and data fields are the same
        assertEquals(patient, patientAllSameFields);

        // Same if other patient is the same instance
        assertEquals(patient, patient);

        // Different if the other object is not Patient
        assertNotEquals(patient, "abc");
    }

    @Test
    void testToString() {
        assertEquals("John Doe;"
            + " Phone: 12345678;"
            + " Age: 31;"
            + " Blood type: A;"
            + " Gender: M;"
            + " Height: 175;"
            + " Weight: 70;"
            + " Medical conditions: [heart failure]", patient.toString());
    }
}
