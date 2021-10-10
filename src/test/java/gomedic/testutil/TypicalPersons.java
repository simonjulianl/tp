package gomedic.testutil;

import static gomedic.testutil.TypicalActivities.getTypicalActivities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.AddressBook;
import gomedic.model.activity.Activity;
import gomedic.model.person.Person;
import gomedic.model.person.doctor.Doctor;
import gomedic.testutil.modelbuilder.DoctorBuilder;
import gomedic.testutil.modelbuilder.PersonBuilder;

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
            new DoctorBuilder().withName("Tom").withPhone("22222")
                    .withDepartment("ENT 3").withId(1).build();


    /**
     * AB3 person implementation
     * To be removed during refactoring
     */
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(CommandTestUtil.VALID_NAME_AMY)
            .withPhone(CommandTestUtil.VALID_PHONE_AMY)
            .withEmail(CommandTestUtil.VALID_EMAIL_AMY).withAddress(CommandTestUtil.VALID_ADDRESS_AMY)
            .withTags(CommandTestUtil.VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(CommandTestUtil.VALID_NAME_BOB)
            .withPhone(CommandTestUtil.VALID_PHONE_BOB)
            .withEmail(CommandTestUtil.VALID_EMAIL_BOB).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
            .withTags(CommandTestUtil.VALID_TAG_HUSBAND, CommandTestUtil.VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and activities.
     * TODO: Maybe refactor this address book to separate file instead of in typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }

        for (Activity activity : getTypicalActivities()) {
            ab.addActivity(activity);
        }

        for (Doctor person : getTypicalDoctors()) {
            ab.addDoctor(person);
        }

        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Doctor> getTypicalDoctors() {
        return new ArrayList<>(Arrays.asList(MAIN_DOCTOR, OTHER_DOCTOR, THIRD_DOCTOR));
    }
}
