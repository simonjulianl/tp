package gomedic.logic.commands;

import static gomedic.logic.commands.addcommand.AddAppointmentCommand.MESSAGE_DOCTOR_NOT_FOUND;
import static gomedic.logic.commands.addcommand.AddAppointmentCommand.MESSAGE_FAIL_TO_GENERATE_REFERRAL;
import static gomedic.logic.commands.addcommand.AddAppointmentCommand.MESSAGE_PATIENT_NOT_FOUND;
import static gomedic.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static gomedic.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static gomedic.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static gomedic.logic.parser.CliSyntax.PREFIX_TITLE;
import static gomedic.model.UserPrefs.ROOT_FOLDER;
import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Time;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;

public class ReferralCommand extends Command {
    public static final String COMMAND_WORD = "referral";

    public static final String MESSAGE_SUCCESS = "Referral has been created at %s";

    private static final String EXAMPLE_MESSAGE =
            "It looks like there may be a small tear in his aorta.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a referral note for a certain patient "
            + "to another doctor. \n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DOCTOR_ID + "DOCTOR_ID "
            + PREFIX_PATIENT_ID + "PATIENT_ID "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Referral of patient A "
            + PREFIX_DOCTOR_ID + "D001 "
            + PREFIX_PATIENT_ID + "P001 "
            + PREFIX_DESCRIPTION + EXAMPLE_MESSAGE;

    private final Id doctorId;
    private final Id patientId;
    private final Description description;
    private final Title title;
    private final Path path;

    public ReferralCommand(Title title, Id doctorId, Id patientId, Description description) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.description = description;
        this.title = title;
        path = Paths.get(ROOT_FOLDER, title + ".pdf");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Doctor specifiedDoctor = getSpecifiedDoctor(model);

        if (specifiedDoctor == null) {
            throw new CommandException(MESSAGE_DOCTOR_NOT_FOUND);
        }

        Patient specifiedPatient = getSpecifiedPatient(model);

        if (specifiedPatient == null) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Time today = new Time(LocalDateTime.now());

        generatePdf();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private Patient getSpecifiedPatient(Model model) {
        return model.getFilteredPatientList()
                .filtered(patient -> patient.getId().equals(patientId))
                .stream()
                .findFirst()
                .orElse(null);
    }

    private Doctor getSpecifiedDoctor(Model model) {
        return model.getFilteredDoctorList()
                .filtered(doctor -> doctor.getId().equals(doctorId))
                .stream()
                .findFirst()
                .orElse(null);
    }

    private void generatePdf() throws CommandException {
        try {
            PdfWriter writer = new PdfWriter(path.toAbsolutePath().toString());
            PdfDocument referral = new PdfDocument(writer);

            referral.addNewPage();

            createPdf(referral);

        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_FAIL_TO_GENERATE_REFERRAL);
        }
    }

    private void createPdf(PdfDocument referral) {
        Document document = new Document(referral);
        String test = "Welcome to Tutorialspoint.";
        Paragraph para = new Paragraph(test);
        document.add(para);
        document.close();
    }
}
