package gomedic.testutil;

import gomedic.logic.commands.EditCommand;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.Doctor;

/**
 * A utility class to help with building EditDoctorDescriptor objects.
 */
public class EditDoctorDescriptorBuilder {

    private final EditCommand.EditDoctorDescriptor descriptor;

    public EditDoctorDescriptorBuilder() {
        descriptor = new EditCommand.EditDoctorDescriptor();
    }

    public EditDoctorDescriptorBuilder(EditCommand.EditDoctorDescriptor descriptor) {
        this.descriptor = new EditCommand.EditDoctorDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDoctorDescriptor} with fields containing {@code person}'s details
     */
    public EditDoctorDescriptorBuilder(Doctor doctor) {
        descriptor = new EditCommand.EditDoctorDescriptor();
        descriptor.setName(doctor.getName());
        descriptor.setPhone(doctor.getPhone());
        descriptor.setDepartment(doctor.getDepartment());
    }

    /**
     * Sets the {@code Name} of the {@code EditDoctorDescriptor} that we are building.
     */
    public EditDoctorDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditDoctorDescriptor} that we are building.
     */
    public EditDoctorDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Department} of the {@code EditDoctorDescriptor} that we are building.
     */
    public EditDoctorDescriptorBuilder withDepartment(String department) {
        descriptor.setDepartment(new Department(department));
        return this;
    }

    public EditCommand.EditDoctorDescriptor build() {
        return descriptor;
    }
}
