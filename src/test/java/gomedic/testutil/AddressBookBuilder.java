package gomedic.testutil;

import gomedic.model.AddressBook;
import gomedic.model.activity.Activity;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code AddressBook ab = new AddressBookBuilder().withPatient(patient).build();}
 */
public class AddressBookBuilder {

    private final AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Activity} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withActivity(Activity activity) {
        addressBook.addActivity(activity);
        return this;
    }

    /**
     * Adds a new {@code Doctor} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withDoctor(Doctor doctor) {
        addressBook.addDoctor(doctor);
        return this;
    }

    /**
     * Adds a new {@code Patient} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPatient(Patient patient) {
        addressBook.addPatient(patient);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
