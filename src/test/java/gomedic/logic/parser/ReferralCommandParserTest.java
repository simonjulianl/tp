package gomedic.logic.parser;

import static gomedic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static gomedic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.ReferralCommand;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.person.doctor.DoctorId;
import gomedic.model.person.patient.PatientId;

class ReferralCommandParserTest {
    private final ReferralCommandParser parser = new ReferralCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ReferralCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_incompleteARgs_throwsParseException() {
        assertParseFailure(parser,
                CommandTestUtil.VALID_PATIENT_REFERRAL_ID
                        + CommandTestUtil.VALID_DOCTOR_REFERRAL_ID,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ReferralCommand.MESSAGE_USAGE));

        assertParseFailure(parser, CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_PATIENT_REFERRAL_ID,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ReferralCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ReferralCommand expectedReferralCommand = new ReferralCommand(
                new Title("Meeting me"),
                new DoctorId(1),
                new PatientId(1),
                new Description("test")
        );

        assertParseSuccess(parser, " ti/Meeting me di/D001 pi/P001 d/test", expectedReferralCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " ti/Meeting me \t di/D001 \t pi/P001 d/test", expectedReferralCommand);

        // jumbled up commands
        assertParseSuccess(parser, " ti/Meeting me pi/P001 d/test di/D001", expectedReferralCommand);
    }

    @Test
    public void parse_missingDesc_returnsFindCommand() {
        // no leading and trailing whitespaces
        ReferralCommand expectedReferralCommand = new ReferralCommand(
                new Title("Meeting me"),
                new DoctorId(1),
                new PatientId(1),
                new Description("")
        );

        assertParseSuccess(parser, " ti/Meeting me di/D001 pi/P001", expectedReferralCommand);
    }
}