package gomedic.testutil;


import gomedic.logic.commands.EditCommand;
import gomedic.logic.commands.addcommand.AddDoctorCommand;
import gomedic.logic.parser.CliSyntax;
import gomedic.model.person.doctor.Doctor;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code doctor}.
     */
    public static String getAddDoctorCommand(Doctor doctor) {
        return AddDoctorCommand.COMMAND_WORD + " " + getDoctorDetails(doctor);
    }

    /**
     * Returns the part of command string for the given {@code doctor}'s details.
     */
    public static String getDoctorDetails(Doctor doctor) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + doctor.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_PHONE + doctor.getPhone().value + " ");
        sb.append(CliSyntax.PREFIX_DEPARTMENT + doctor.getDepartment().departmentName);
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditDoctorDescriptor}'s details.
     */
    public static String getEditDoctorDescriptorDetails(EditCommand.EditDoctorDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(CliSyntax.PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getDepartment()
                .ifPresent(department ->
                        sb.append(CliSyntax.PREFIX_DEPARTMENT).append(department.departmentName).append(" "));
        return sb.toString();
    }
}
