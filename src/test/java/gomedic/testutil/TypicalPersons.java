package gomedic.testutil;

import static gomedic.testutil.TypicalActivities.getTypicalActivities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gomedic.model.AddressBook;
import gomedic.model.activity.Activity;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;
import gomedic.testutil.modelbuilder.DoctorBuilder;
import gomedic.testutil.modelbuilder.PatientBuilder;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    /**
     * GoMedic doctors
     */
    public static final Doctor MAIN_DOCTOR = new DoctorBuilder().build();
    public static final Doctor OTHER_DOCTOR =
            new DoctorBuilder().withName("Smith John").withPhone("77777777").withDepartment("ENT").withId(2).build();
    public static final Doctor THIRD_DOCTOR =
            new DoctorBuilder().withName("Joe Smith").withPhone("55555555").withDepartment("Xray").withId(3).build();
    public static final Doctor NOT_IN_TYPICAL_DOCTOR =
            new DoctorBuilder().withName("Midnight coding lol").withPhone("11111111")
                    .withDepartment("ENT 2").withId(4).build();
    public static final Doctor DUPLICATE_DOCTOR =
            new DoctorBuilder().withName("Tom").withPhone("22222222")
                    .withDepartment("ENT 3").withId(1).build();

    /**
     * GoMedic patients
     */
    public static final Patient MAIN_PATIENT = new PatientBuilder().build();
    public static final Patient OTHER_PATIENT =
        new PatientBuilder().withName("Smith John").withPhone("77777777").withAge("41").withBloodType("O+")
            .withGender("O").withHeight("166").withWeight("55").withId(2).build();
    public static final Patient THIRD_PATIENT =
        new PatientBuilder().withName("Joe Smith").withPhone("55555555").withAge("35").withId(3).build();
    public static final Patient NOT_IN_TYPICAL_PATIENT =
        new PatientBuilder().withName("Midnight coding lol").withPhone("11111111").withAge("28").withId(4).build();


    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and activities.
     * TODO: Maybe refactor this address book to separate file instead of in typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();

        for (Activity activity : getTypicalActivities()) {
            ab.addActivity(activity);
        }

        for (Doctor person : getTypicalDoctors()) {
            ab.addDoctor(person);
        }

        for (Patient person : getTypicalPatients()) {
            ab.addPatient(person);
        }

        return ab;
    }

    public static List<Doctor> getTypicalDoctors() {
        return new ArrayList<>(Arrays.asList(MAIN_DOCTOR, OTHER_DOCTOR, THIRD_DOCTOR));
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(MAIN_PATIENT, OTHER_PATIENT, THIRD_PATIENT));
    }
}
