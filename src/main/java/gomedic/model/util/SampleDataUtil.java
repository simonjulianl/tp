package gomedic.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import gomedic.model.AddressBook;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.commonfield.Time;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;
import gomedic.model.person.patient.Age;
import gomedic.model.person.patient.BloodType;
import gomedic.model.person.patient.Gender;
import gomedic.model.person.patient.Height;
import gomedic.model.person.patient.Patient;
import gomedic.model.person.patient.PatientId;
import gomedic.model.person.patient.Weight;
import gomedic.model.tag.Tag;
import gomedic.model.userprofile.Organization;
import gomedic.model.userprofile.Position;
import gomedic.model.userprofile.UserProfile;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static UserProfile getSampleUserProfile() {
        return new UserProfile(new Name("Dr Rosalind Soo"),
                new Position("Associate Consultant"),
                new Department("Department of Cardiology"),
                new Organization("National University Hospital"));
    }

    public static Doctor[] getSampleDoctors() {
        int idx = 1;
        return new Doctor[]{
                new Doctor(new Name("Alex Yeoh"), new Phone("87438807"), new DoctorId(idx++),
                        new Department("ENT")),
                new Doctor(new Name("Bernice Yu"), new Phone("99272758"), new DoctorId(idx++),
                        new Department("XRAY")),
                new Doctor(new Name("Charlotte Oliveiro"), new Phone("93210283"), new DoctorId(idx++),
                        new Department("Surgical")),
                new Doctor(new Name("David Li"), new Phone("91031282"), new DoctorId(idx++),
                        new Department("Pediatrics")),
                new Doctor(new Name("Irfan Ibrahim"), new Phone("92492021"), new DoctorId(idx++),
                        new Department("Neurology")),
                new Doctor(new Name("Roy Balakrishnan"), new Phone("92624417"), new DoctorId(idx),
                        new Department("Cardiology"))
        };
    }

    /**
     * Returns sample activity together with appointments.
     * Sample activities are guaranteed to be valid.
     */
    public static Activity[] getSampleActivity() {
        return new Activity[]{
                new Activity(new ActivityId(1),
                        new Time(LocalDateTime.now().minusDays(2)),
                        new Time(LocalDateTime.now().minusDays(2).plusHours(1)),
                        new Title("My First Activity"),
                        new Description("Congratulations!")
                ),
                new Activity(new ActivityId(2),
                        new Time(LocalDateTime.now()),
                        new Time(LocalDateTime.now().plusHours(2)),
                        new Title("Meeting"),
                        new Description("Head of cardiology about Grant")
                ),
                new Activity(new ActivityId(3),
                        new PatientId(1),
                        new Time(LocalDateTime.now().plusDays(1).minusHours(1)),
                        new Time(LocalDateTime.now().plusDays(1)),
                        new Title("Appointment with Johnny"),
                        new Description("Johnny has stomach ulcer for 5 months.")
                ),
                new Activity(new ActivityId(4),
                        new PatientId(2),
                        new Time(LocalDateTime.now().plusDays(2).minusHours(5)),
                        new Time(LocalDateTime.now().plusDays(2).minusHours(3)),
                        new Title("Appointment with Simon"),
                        new Description("He has headache after doing AB3.")
                ),
                new Activity(new ActivityId(5),
                        new PatientId(2),
                        new Time(LocalDateTime.now().plusDays(5).minusHours(5)),
                        new Time(LocalDateTime.now().plusDays(5).minusHours(3)),
                        new Title("Follow up with Simon"),
                        new Description("Check his condition after doing CS2103T.")
                ),
                new Activity(new ActivityId(6),
                        new PatientId(4),
                        new Time(LocalDateTime.now().plusDays(10).minusHours(5)),
                        new Time(LocalDateTime.now().plusDays(10).minusHours(3)),
                        new Title("Radian Health Checkup"),
                        new Description("Check his headache condition again.")
                ),
                new Activity(new ActivityId(7),
                        new PatientId(5),
                        new Time(LocalDateTime.now().plusDays(14).minusHours(10)),
                        new Time(LocalDateTime.now().plusDays(14).minusHours(8)),
                        new Title("Rano checkup"),
                        new Description("Check his schizophrenia condition, often forget things.")
                )
        };
    }

    /**
     * Returns sample patients.
     * Sample patients are guaranteed to be valid.
     */
    public static Patient[] getSamplePatient() {
        return new Patient[]{
                new Patient(
                        new Name("Jonny"),
                        new Phone("99999999"),
                        new PatientId(1),
                        new Age("20"),
                        new BloodType("A+"),
                        new Gender("M"),
                        new Height("170"),
                        new Weight("65"),
                        getTagSet("Stomach Ulcer")
                ),
                new Patient(
                        new Name("Simon"),
                        new Phone("99991234"),
                        new PatientId(2),
                        new Age("21"),
                        new BloodType("AB-"),
                        new Gender("M"),
                        new Height("175"),
                        new Weight("80"),
                        getTagSet("CS2102", "CS2103T")
                ),
                new Patient(
                        new Name("Lauw"),
                        new Phone("99993456"),
                        new PatientId(3),
                        new Age("25"),
                        new BloodType("O+"),
                        new Gender("M"),
                        new Height("160"),
                        new Weight("90"),
                        getTagSet("Stomach Ulcer")
                ),
                new Patient(
                        new Name("Radian"),
                        new Phone("93413436"),
                        new PatientId(4),
                        new Age("15"),
                        new BloodType("AB+"),
                        new Gender("M"),
                        new Height("190"),
                        new Weight("75"),
                        getTagSet("Headache")
                ),
                new Patient(
                        new Name("Rano Krisno"),
                        new Phone("91239876"),
                        new PatientId(5),
                        new Age("80"),
                        new BloodType("A+"),
                        new Gender("M"),
                        new Height("170"),
                        new Weight("50"),
                        getTagSet("Schizophrenia")
                )
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        sampleAb.setUserProfile(getSampleUserProfile());

        for (Doctor sampleDoctor : getSampleDoctors()) {
            if (!sampleAb.hasDoctor(sampleDoctor)) {
                sampleAb.addDoctor(sampleDoctor);
            }
        }

        for (Patient samplePatient : getSamplePatient()) {
            sampleAb.addPatient(samplePatient);
        }

        for (Activity sampleActivity : getSampleActivity()) {
            sampleAb.addActivity(sampleActivity);
        }

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
