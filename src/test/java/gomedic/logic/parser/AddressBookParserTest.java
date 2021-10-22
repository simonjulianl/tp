package gomedic.logic.parser;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.ExitCommand;
import gomedic.logic.commands.HelpCommand;
import gomedic.logic.commands.ProfileCommand;
import gomedic.logic.commands.addcommand.AddDoctorCommand;
import gomedic.logic.commands.clearcommand.ClearActivityCommand;
import gomedic.logic.commands.clearcommand.ClearCommand;
import gomedic.logic.commands.clearcommand.ClearDoctorCommand;
import gomedic.logic.commands.clearcommand.ClearPatientCommand;
import gomedic.logic.commands.deletecommand.DeleteDoctorCommand;
import gomedic.logic.commands.deletecommand.DeletePatientCommand;
import gomedic.logic.commands.editcommand.EditDoctorCommand;
import gomedic.logic.commands.findcommand.FindActivityCommand;
import gomedic.logic.commands.findcommand.FindDoctorCommand;
import gomedic.logic.commands.findcommand.FindPatientCommand;
import gomedic.logic.commands.listcommand.ListActivityCommand;
import gomedic.logic.commands.listcommand.ListDoctorCommand;
import gomedic.logic.commands.listcommand.ListPatientCommand;
import gomedic.logic.commands.viewcommand.ViewPatientCommand;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.activity.Activity;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;
import gomedic.model.person.patient.Patient;
import gomedic.model.util.ActivityTitleContainsKeywordsPredicate;
import gomedic.model.person.patient.PatientId;
import gomedic.model.userprofile.UserProfile;
import gomedic.model.util.NameContainsKeywordsPredicate;
import gomedic.testutil.CommandGenerationUtils;
import gomedic.testutil.editdescriptorbuilder.EditDoctorDescriptorBuilder;
import gomedic.testutil.modelbuilder.DoctorBuilder;
import gomedic.testutil.modelbuilder.UserProfileBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_addDoctor() throws Exception {
        Doctor doctor = new DoctorBuilder().build();
        AddDoctorCommand command =
                (AddDoctorCommand) parser.parseCommand(CommandGenerationUtils.getAddDoctorCommand(doctor));
        assertEquals(new AddDoctorCommand(doctor.getName(), doctor.getPhone(), doctor.getDepartment()), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearActivity() throws Exception {
        assertTrue(parser.parseCommand(ClearActivityCommand.COMMAND_WORD) instanceof ClearActivityCommand);
        assertTrue(parser.parseCommand(ClearActivityCommand.COMMAND_WORD + " 3") instanceof ClearActivityCommand);
    }

    @Test
    public void parseCommand_clearDoctor() throws Exception {
        assertTrue(parser.parseCommand(ClearDoctorCommand.COMMAND_WORD) instanceof ClearDoctorCommand);
        assertTrue(parser.parseCommand(ClearDoctorCommand.COMMAND_WORD + " 3") instanceof ClearDoctorCommand);
    }

    @Test
    public void parseCommand_clearPatient() throws Exception {
        assertTrue(parser.parseCommand(ClearPatientCommand.COMMAND_WORD) instanceof ClearPatientCommand);
        assertTrue(parser.parseCommand(ClearPatientCommand.COMMAND_WORD + " 3") instanceof ClearPatientCommand);
    }

    @Test
    public void parseCommand_deleteDoctor() throws Exception {
        DoctorId testId = new DoctorId(1);
        DeleteDoctorCommand command = (DeleteDoctorCommand) parser.parseCommand(
                DeleteDoctorCommand.COMMAND_WORD + " " + testId.toString());
        assertEquals(new DeleteDoctorCommand(testId), command);
    }

    @Test
    public void parseCommand_deletePatient() throws Exception {
        PatientId testId = new PatientId(1);
        DeletePatientCommand command = (DeletePatientCommand) parser.parseCommand(
            DeletePatientCommand.COMMAND_WORD + " " + testId.toString());
        assertEquals(new DeletePatientCommand(testId), command);
    }

    @Test
    public void parseCommand_viewPatient() throws Exception {
        PatientId testId = new PatientId(1);
        ViewPatientCommand command = (ViewPatientCommand) parser.parseCommand(
            ViewPatientCommand.COMMAND_WORD + " " + testId.toString());
        assertEquals(new ViewPatientCommand(testId), command);
    }

    @Test
    public void parseCommand_editDoctor() throws Exception {
        Doctor doctor = new DoctorBuilder().build();
        EditDoctorCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder(doctor).build();
        EditDoctorCommand command = (EditDoctorCommand) parser.parseCommand(EditDoctorCommand.COMMAND_WORD
            + " " + CliSyntax.PREFIX_ID + doctor.getId()
            + " " + CommandGenerationUtils.getEditDoctorDescriptorDetails(descriptor));
        assertEquals(new EditDoctorCommand(doctor.getId(), descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findDoctor() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindDoctorCommand command = (FindDoctorCommand) parser.parseCommand(
                FindDoctorCommand.COMMAND_WORD + " " + "n/" + String.join(" ", keywords));
        assertEquals(new FindDoctorCommand(new NameContainsKeywordsPredicate<Doctor>(keywords)), command);
    }

    @Test
    public void parseCommand_findPatient() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPatientCommand command = (FindPatientCommand) parser.parseCommand(
                FindPatientCommand.COMMAND_WORD + " " + "n/" + String.join(" ", keywords));
        assertEquals(new FindPatientCommand(new NameContainsKeywordsPredicate<Patient>(keywords)), command);
    }

    @Test
    public void parseCommand_findActivity() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindActivityCommand command = (FindActivityCommand) parser.parseCommand(
                FindActivityCommand.COMMAND_WORD + " " + "ti/" + String.join(" ", keywords));
        assertEquals(new FindActivityCommand(new ActivityTitleContainsKeywordsPredicate<Activity>(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listDoctor() throws Exception {
        assertTrue(parser.parseCommand(ListDoctorCommand.COMMAND_WORD) instanceof ListDoctorCommand);
        assertTrue(parser.parseCommand(ListDoctorCommand.COMMAND_WORD + " 3") instanceof ListDoctorCommand);
    }

    @Test
    public void parseCommand_listPatient() throws Exception {
        assertTrue(parser.parseCommand(ListPatientCommand.COMMAND_WORD) instanceof ListPatientCommand);
        assertTrue(parser.parseCommand(ListPatientCommand.COMMAND_WORD + " 3") instanceof ListPatientCommand);
    }

    @Test
    public void parseCommand_listActivity() throws Exception {
        assertTrue(parser.parseCommand(ListActivityCommand.COMMAND_WORD) instanceof ListActivityCommand);
        assertTrue(parser.parseCommand(ListActivityCommand.COMMAND_WORD + " 3") instanceof ListActivityCommand);
    }

    @Test
    public void parseCommand_updateProfile() throws Exception {
        UserProfile userProfile = new UserProfileBuilder().build();
        ProfileCommand command =
                (ProfileCommand) parser.parseCommand(ProfileCommand.COMMAND_WORD + " "
                        + CliSyntax.PREFIX_NAME + userProfile.getName() + " "
                        + CliSyntax.PREFIX_POSITION + userProfile.getPosition() + " "
                        + CliSyntax.PREFIX_DEPARTMENT + userProfile.getDepartment() + " "
                        + CliSyntax.PREFIX_ORGANIZATION + userProfile.getOrganization());
        assertEquals(new ProfileCommand(userProfile.getName(), userProfile.getPosition(), userProfile.getDepartment(),
                userProfile.getOrganization()), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(
                        Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        String command = "unknownCommand";
        assertThrows(ParseException.class,
                String.format(Messages.MESSAGE_UNKNOWN_COMMAND, command), () -> parser.parseCommand(command));
    }
}
