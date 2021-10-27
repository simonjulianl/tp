package gomedic.commons.core;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class MessagesTest {
    @Test
    public void getSuggestions_misspelledCommandType_correctSuggestionsReturned() {
        // clear command
        String wrongCommand = "clara";
        List<String> closestWords = List.of(
                "clear t/patient    ",
                "clear t/doctor    ",
                "clear t/activity    ",
                "clear    ");
        String reply = String.format(Messages.MESSAGE_UNKNOWN_COMMAND + " "
                + "You can choose from these commands instead: \n", wrongCommand);
        for (String s : closestWords) {
            reply += s;
        }
        assertEquals(reply, Messages.getSuggestions(wrongCommand));

        // for view command
        wrongCommand = "vie";
        closestWords = List.of("view t/patient    ");
        reply = String.format(Messages.MESSAGE_UNKNOWN_COMMAND + " "
                + "You can choose from these commands instead: \n", wrongCommand);
        for (String s : closestWords) {
            reply += s;
        }
        assertEquals(reply, Messages.getSuggestions(wrongCommand));

        // for add command
        wrongCommand = "adl";
        closestWords = List.of(
                "add t/patient    ",
                "add t/doctor    ",
                "add t/activity    ",
                "add t/appointment    ");
        reply = String.format(Messages.MESSAGE_UNKNOWN_COMMAND + " "
                + "You can choose from these commands instead: \n", wrongCommand);
        for (String s : closestWords) {
            reply += s;
        }
        assertEquals(reply, Messages.getSuggestions(wrongCommand));
    }

    @Test
    public void getSuggestions_misspelledCommandTarget_correctSuggestionsReturned() {
        String wrongCommand = "add t/patet";
        List<String> closestWords = List.of("add t/patient    ");
        String reply = String.format(Messages.MESSAGE_UNKNOWN_COMMAND + " "
                + "You can choose from these commands instead: \n", wrongCommand);
        for (String s : closestWords) {
            reply += s;
        }
        assertEquals(reply, Messages.getSuggestions(wrongCommand));
    }

    @Test
    public void getSuggestions_doubleMisspellings_correctSuggestionsReturned() {
        String wrongCommand = "ald t/pacit";
        List<String> closestWords = List.of("add t/patient    ", "add t/activity    ");
        String reply = String.format(Messages.MESSAGE_UNKNOWN_COMMAND + " "
                + "You can choose from these commands instead: \n", wrongCommand);
        for (String s : closestWords) {
            reply += s;
        }
        assertEquals(reply, Messages.getSuggestions(wrongCommand));
    }

    @Test
    public void getSuggestions_garbageCommand_noSuggestionsReturned() {
        String wrongCommand = "ajadsjfksdkj";
        String reply = String.format(Messages.MESSAGE_UNKNOWN_COMMAND, wrongCommand);
        assertEquals(reply, Messages.getSuggestions(wrongCommand));
    }
}
