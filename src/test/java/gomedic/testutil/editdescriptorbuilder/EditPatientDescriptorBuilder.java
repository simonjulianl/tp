package gomedic.testutil.editdescriptorbuilder;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gomedic.logic.commands.editcommand.EditPatientCommand;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.patient.Age;
import gomedic.model.person.patient.BloodType;
import gomedic.model.person.patient.Gender;
import gomedic.model.person.patient.Height;
import gomedic.model.person.patient.Patient;
import gomedic.model.person.patient.Weight;
import gomedic.model.tag.Tag;

/**
 * A utility class to help with building EditPatientDescriptor objects.
 */
public class EditPatientDescriptorBuilder {

    private final EditPatientCommand.EditPatientDescriptor descriptor;

    public EditPatientDescriptorBuilder() {
        descriptor = new EditPatientCommand.EditPatientDescriptor();
    }

    public EditPatientDescriptorBuilder(EditPatientCommand.EditPatientDescriptor descriptor) {
        this.descriptor = new EditPatientCommand.EditPatientDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPatientDescriptor} with fields containing {@code person}'s details
     */
    public EditPatientDescriptorBuilder(Patient patient) {
        descriptor = new EditPatientCommand.EditPatientDescriptor();
        descriptor.setName(patient.getName());
        descriptor.setPhone(patient.getPhone());
        descriptor.setAge(patient.getAge());
        descriptor.setBloodType(patient.getBloodType());
        descriptor.setGender(patient.getGender());
        descriptor.setHeight(patient.getHeight());
        descriptor.setWeight(patient.getWeight());
        descriptor.setMedicalConditions(patient.getMedicalConditions());
    }

    /**
     * Sets the {@code Name} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withAge(String age) {
        descriptor.setAge(new Age(age));
        return this;
    }

    /**
     * Sets the {@code BloodType} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withBloodType(String bloodType) {
        descriptor.setBloodType(new BloodType(bloodType));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Sets the {@code Height} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withHeight(String height) {
        descriptor.setHeight(new Height(height));
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withWeight(String weight) {
        descriptor.setWeight(new Weight(weight));
        return this;
    }

    /**
     * Parses the {@code medicalConditions} into a {@code Set<Tag>} and set it to the {@code EditPatientDescriptor}
     * that we are building.
     */
    public EditPatientDescriptorBuilder withMedicalConditions(String... medicalConditions) {
        Set<Tag> tagSet = Stream.of(medicalConditions).map(Tag::new).collect(Collectors.toSet());
        descriptor.setMedicalConditions(tagSet);
        return this;
    }

    public EditPatientCommand.EditPatientDescriptor build() {
        return descriptor;
    }
}
