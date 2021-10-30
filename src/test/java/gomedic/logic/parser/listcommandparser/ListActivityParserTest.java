package gomedic.logic.parser.listcommandparser;

import org.junit.jupiter.api.Test;

import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.listcommand.ListActivityCommand;
import gomedic.logic.parser.CommandParserTestUtil;

public class ListActivityParserTest {
    private final ListActivityParser parser = new ListActivityParser();

    @Test
    public void parse_allDefault_success() {
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.PREAMBLE_WHITESPACE,
                new ListActivityCommand(ListActivityCommand.Sort.ID, ListActivityCommand.Period.ALL));

    }

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.PREAMBLE_WHITESPACE
                        + CommandTestUtil.VALID_SORT_FLAG_ID
                        + CommandTestUtil.VALID_PERIOD_FLAG_ALL,
                new ListActivityCommand(ListActivityCommand.Sort.ID, ListActivityCommand.Period.ALL));

        // multiple start flag
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_SORT_FLAG_START
                        + CommandTestUtil.VALID_SORT_FLAG_ID
                        + CommandTestUtil.VALID_PERIOD_FLAG_ALL,
                new ListActivityCommand(ListActivityCommand.Sort.ID, ListActivityCommand.Period.ALL));

        // multiple period flag
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.VALID_SORT_FLAG_START
                        + CommandTestUtil.VALID_PERIOD_FLAG_ALL
                        + CommandTestUtil.VALID_PERIOD_FLAG_TODAY,
                new ListActivityCommand(ListActivityCommand.Sort.START, ListActivityCommand.Period.TODAY));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.PREAMBLE_WHITESPACE
                        + CommandTestUtil.VALID_PERIOD_FLAG_TODAY,
                new ListActivityCommand(ListActivityCommand.Sort.ID, ListActivityCommand.Period.TODAY));

        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.PREAMBLE_WHITESPACE
                        + CommandTestUtil.VALID_SORT_FLAG_ID,
                new ListActivityCommand(ListActivityCommand.Sort.ID, ListActivityCommand.Period.ALL));

        // random flags, ignore irrelevant flags.
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.INVALID_DESC_DESCRIPTION,
                new ListActivityCommand(ListActivityCommand.Sort.ID, ListActivityCommand.Period.ALL));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid sort flag
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.INVALID_SORT_FLAG,
                ListActivityCommand.Sort.MESSAGE_CONSTRAINTS);

        // invalid period flag
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.INVALID_PERIOD_FLAG,
                ListActivityCommand.Period.MESSAGE_CONSTRAINTS);

        // both invalid period and sort flag
        CommandParserTestUtil.assertParseFailure(parser,
                CommandTestUtil.INVALID_SORT_FLAG
                        + CommandTestUtil.INVALID_PERIOD_FLAG,
                ListActivityCommand.Sort.MESSAGE_CONSTRAINTS);
    }
}
