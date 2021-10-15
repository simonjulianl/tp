package gomedic.commons.core;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class MessagesTest {
    @Test
    public void getSuggestions_misspelledCommandType_correctSuggestionsReturned() {
        String wrongCommand = "clara";
        List<String> closestWords = List.of(
                "clear t/patient    ",
                "clear t/doctor    ",
                "clear t/activity    ");
        String reply = String.format("Sorry, %s is an invalid command. "
                + "You can choose from these commands instead: \n", wrongCommand);
        for (String s : closestWords) {
            reply += s;
        }
        assertEquals(reply, Messages.getSuggestions(wrongCommand));
    }

    @Test
    public void getSuggestions_misspelledCommandTarget_correctSuggestionsReturned() {
        String wrongCommand = "add t/patet";
        List<String> closestWords = List.of(
                "add t/patient    ",
                "edit t/patient    ",
                "add t/doctor    ",
                "view t/patient    ",
                "list t/patient    "
                );
        String reply = String.format("Sorry, %s is an invalid command. "
                + "You can choose from these commands instead: \n", wrongCommand);
        for (String s : closestWords) {
            reply += s;
        }
        assertEquals(reply, Messages.getSuggestions(wrongCommand));
    }
    @Test
    public void getSuggestions_garbageCommand_noSuggestionsReturned() {
        String wrongCommand = "ajadsjfksdkj";
        String reply = String.format("Sorry, %s is an invalid command.", wrongCommand);
        assertEquals(reply, Messages.getSuggestions(wrongCommand));
    }
}
