package gomedic.testutil;

import gomedic.model.AddressBook;
import gomedic.model.activity.Activity;
import gomedic.model.person.Person;
import gomedic.model.person.doctor.Doctor;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
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
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
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

    public AddressBook build() {
        return addressBook;
    }
}
