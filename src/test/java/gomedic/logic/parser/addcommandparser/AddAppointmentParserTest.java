package gomedic.logic.parser.addcommandparser;

import static gomedic.testutil.TypicalActivities.APPOINTMENT;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.addcommand.AddAppointmentCommand;
import gomedic.logic.parser.CommandParserTestUtil;
import gomedic.model.activity.Activity;
import gomedic.model.commonfield.Id;



public class AddAppointmentParserTest {
    private final AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Activity expectedAppointment = APPOINTMENT;

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.PREAMBLE_WHITESPACE
                        + CommandTestUtil.VALID_PATIENT_ID
                        + CommandTestUtil.VALID_DESC_START_TIME_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_END_TIME_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_TITLE_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_APPOINTMENT_DESCRIPTION, new AddAppointmentCommand(
                        expectedAppointment.getPatientId(),
                        expectedAppointment.getStartTime(),
                        expectedAppointment.getEndTime(),
                        expectedAppointment.getTitle(),
                        expectedAppointment.getDescription()));

        // multiple start time - last of the argument will be accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_PATIENT_ID
                        + CommandTestUtil.VALID_DESC_START_TIME_PAPER_REVIEW
                        + CommandTestUtil.VALID_DESC_START_TIME_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_END_TIME_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_TITLE_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_APPOINTMENT_DESCRIPTION, new AddAppointmentCommand(
                        expectedAppointment.getPatientId(),
                        expectedAppointment.getStartTime(),
                        expectedAppointment.getEndTime(),
                        expectedAppointment.getTitle(),
                        expectedAppointment.getDescription()));

        // multiple end time
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_PATIENT_ID
                        + CommandTestUtil.VALID_DESC_START_TIME_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_END_TIME_PAPER_REVIEW
                        + CommandTestUtil.VALID_DESC_END_TIME_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_TITLE_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_APPOINTMENT_DESCRIPTION, new AddAppointmentCommand(
                        expectedAppointment.getPatientId(),
                        expectedAppointment.getStartTime(),
                        expectedAppointment.getEndTime(),
                        expectedAppointment.getTitle(),
                        expectedAppointment.getDescription()));

        // multiple title, last title accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_PATIENT_ID
                        + CommandTestUtil.VALID_DESC_START_TIME_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_END_TIME_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_TITLE_PAPER_REVIEW
                        + CommandTestUtil.VALID_DESC_TITLE_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_APPOINTMENT_DESCRIPTION, new AddAppointmentCommand(
                        expectedAppointment.getPatientId(),
                        expectedAppointment.getStartTime(),
                        expectedAppointment.getEndTime(),
                        expectedAppointment.getTitle(),
                        expectedAppointment.getDescription()));

        // multiple desc, last desc accepted
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_PATIENT_ID
                        + CommandTestUtil.VALID_DESC_START_TIME_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_END_TIME_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_TITLE_APPOINTMENT
                        + CommandTestUtil.VALID_DESC_APPOINTMENT_DESCRIPTION
                        + CommandTestUtil.VALID_DESC_APPOINTMENT_DESCRIPTION, new AddAppointmentCommand(
                        expectedAppointment.getPatientId(),
                        expectedAppointment.getStartTime(),
                        expectedAppointment.getEndTime(),
                        expectedAppointment.getTitle(),
                        expectedAppointment.getDescription()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE);
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid patient id
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.INVALID_PATIENT_ID
                        + CommandTestUtil.VALID_DESC_START_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                        + CommandTestUtil.VALID_DESC_TITLE_MEETING
                        + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION, Id.MESSAGE_CONSTRAINTS);
    }
}
