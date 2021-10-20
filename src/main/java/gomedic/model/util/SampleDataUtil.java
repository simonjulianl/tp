package gomedic.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import gomedic.model.AddressBook;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.activity.Description;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;
import gomedic.model.tag.Tag;
import gomedic.model.userprofile.UserProfile;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static UserProfile getSampleUserProfile() {
        return new UserProfile(new Name("User profile not set yet"),
                new Description("Refer to profile command to set profile description"));
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

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        sampleAb.setUserProfile(getSampleUserProfile());

        for (Doctor sampleDoctor : getSampleDoctors()) {
            if (!sampleAb.hasDoctor(sampleDoctor)) {
                sampleAb.addDoctor(sampleDoctor);
            }
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
