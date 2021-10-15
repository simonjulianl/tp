package gomedic.testutil.editdescriptorbuilder;

import gomedic.logic.commands.editcommand.EditActivityCommand;
import gomedic.model.activity.Activity;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Time;

/**
 * A utility class to help with building EditDoctorDescriptor objects.
 */
public class EditActivityDescriptorBuilder {

    private final EditActivityCommand.EditActivityDescriptor descriptor;

    public EditActivityDescriptorBuilder() {
        descriptor = new EditActivityCommand.EditActivityDescriptor();
    }

    public EditActivityDescriptorBuilder(EditActivityCommand.EditActivityDescriptor descriptor) {
        this.descriptor = new EditActivityCommand.EditActivityDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditActivityDescriptor} with fields containing {@code activity}'s details
     */
    public EditActivityDescriptorBuilder(Activity activity) {
        descriptor = new EditActivityCommand.EditActivityDescriptor();
        descriptor.setStartTime(activity.getStartTime());
        descriptor.setEndTime(activity.getEndTime());
        descriptor.setTitle(activity.getTitle());
        descriptor.setDescription(activity.getDescription());
    }

    /**
     * Sets the {@code StartTime} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new Time(startTime));
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEndTime(new Time(endTime));
        return this;
    }

    /**
     * Sets the {@code Title} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }


    /**
     * Sets the {@code Description} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    public EditActivityCommand.EditActivityDescriptor build() {
        return descriptor;
    }
}
