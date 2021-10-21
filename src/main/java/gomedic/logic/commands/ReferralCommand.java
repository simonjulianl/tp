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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Id;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;

public class ReferralCommand extends Command {
    public static final String COMMAND_WORD = "referral";

    public static final String MESSAGE_SUCCESS = "Referral has been created at %s";

    // TODO : Add my profile into the pdf
    public static final Paragraph NEWLINE = new Paragraph("\n");
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
    private final Path path;

    public ReferralCommand(Title title, Id doctorId, Id patientId, Description description) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.description = description;
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

        generatePdf(specifiedDoctor, specifiedPatient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, path.toAbsolutePath()));
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

    private void generatePdf(Doctor specifiedDoctor, Patient specifiedPatient) throws CommandException {
        try {
            PdfWriter writer = new PdfWriter(path.toAbsolutePath().toString());
            PdfDocument referral = new PdfDocument(writer);

            referral.setTagged();
            referral.addNewPage();

            createPdf(referral, specifiedDoctor, specifiedPatient);

        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_FAIL_TO_GENERATE_REFERRAL);
        }
    }

    private void createPdf(PdfDocument referral,
                           Doctor specifiedDoctor,
                           Patient specifiedPatient) {
        Document document = new Document(referral);
        document.setMargins(30f, 50f, 30f, 50f);

        Paragraph title = getTitle();
        title.getAccessibilityProperties().setRole(StandardRoles.H1);
        document.add(title);
        document.add(NEWLINE);

        Paragraph date = getDate();
        document.add(date);
        document.add(NEWLINE);

        Paragraph identity = getIdentity();
        document.add(identity);
        document.add(NEWLINE);

        Paragraph salutation = getSalutation(specifiedDoctor);
        document.add(salutation);
        document.add(NEWLINE);

        Paragraph bodyTemplate = getBodyTemplate(specifiedPatient);
        document.add(bodyTemplate);
        document.add(NEWLINE);

        Paragraph signOff = getSignOff();
        document.add(signOff);

        document.close();
    }

    private Paragraph getSignOff() {
        // TODO : Edit the identity here
        String signOff = "Thank you, \n" + "Simon Julian Lauw";
        return new Paragraph(signOff)
                .setFontSize(14f);
    }

    private Paragraph getSalutation(Doctor specifiedDoctor) {
        String salutation = "Dear Dr." + specifiedDoctor.getName();
        return new Paragraph(salutation)
                .setFontSize(14f);
    }

    private Paragraph getBodyTemplate(Patient specifiedPatient) {
        String objectPronoun = specifiedPatient.getGender().gender.equals("M") ? "Him" : "Her";
        String subjectPronoun = specifiedPatient.getGender().gender.equals("M") ? "He" : "She";
        String possessivePronoun = specifiedPatient.getGender().gender.equals("M") ? "His" : "Her";
        String salutation = specifiedPatient.getGender().gender.equals("M") ? "Mr" : "Mrs/Ms";

        String firstParaTemplate = "I would like to refer %s. %s to your clinic for a "
                + "check-up as well as urgent treatment. "
                + "%s is a %s years old with %s. "
                + "I am afraid that I do not have the necessary resources "
                + "to treat %s as %s requires according to %s condition. \n";

        String secondParaTemplate = "\n %s \n" +
                "\n Please kindly look into %s case and give %s the treatment required.";

        String medicalConditions = specifiedPatient
                .getMedicalConditions()
                .stream()
                .map(tag -> tag.toString().substring(1, tag.toString().length() - 1))
                .collect(Collectors.joining(","));

        String firstPara = String.format(firstParaTemplate,
                salutation,
                specifiedPatient.getName().fullName,
                subjectPronoun,
                specifiedPatient.getAge().age,
                medicalConditions.toLowerCase(),
                objectPronoun.toLowerCase(),
                subjectPronoun.toLowerCase(),
                possessivePronoun.toLowerCase()
        );

        String secondPara = String.format(secondParaTemplate,
                description.toString(),
                possessivePronoun.toLowerCase(),
                objectPronoun.toLowerCase()
        );

        String overall = firstPara + secondPara;

        return new Paragraph(overall)
                .setFontSize(14f);
    }

    private Paragraph getIdentity() {
        // TODO : add the identity from the profile later
        String identity = "Simon Julian Lauw \n"
                + "Software Engineers \n"
                + "Department of Dermatology \n"
                + "National University Hospital \n";

        return new Paragraph(identity)
                .setFontSize(14f);
    }

    private Paragraph getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
        String date = LocalDate.now().format(formatter);

        return new Paragraph(date).setFontSize(14f);
    }

    private Paragraph getTitle() {
        String title = "Medical Referral Letter";

        return new Paragraph(title)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20f)
                .setBold();
    }
}
