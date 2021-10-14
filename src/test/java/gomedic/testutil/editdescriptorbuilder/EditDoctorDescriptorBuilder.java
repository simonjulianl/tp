package gomedic.testutil.editdescriptorbuilder;

import gomedic.logic.commands.editcommand.EditDoctorCommand;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.Doctor;

/**
 * A utility class to help with building EditDoctorDescriptor objects.
 */
public class EditDoctorDescriptorBuilder {

    private final EditDoctorCommand.EditDoctorDescriptor descriptor;

    public EditDoctorDescriptorBuilder() {
        descriptor = new EditDoctorCommand.EditDoctorDescriptor();
    }

    public EditDoctorDescriptorBuilder(EditDoctorCommand.EditDoctorDescriptor descriptor) {
        this.descriptor = new EditDoctorCommand.EditDoctorDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDoctorDescriptor} with fields containing {@code person}'s details
     */
    public EditDoctorDescriptorBuilder(Doctor doctor) {
        descriptor = new EditDoctorCommand.EditDoctorDescriptor();
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

    public EditDoctorCommand.EditDoctorDescriptor build() {
        return descriptor;
    }
}
