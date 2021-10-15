package gomedic.commons.core;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class MessagesTest {
    @Test
    public void getSuggestions_misspelledCommandType_correctSuggestionsReturned() {
        String wrongCommand = "clara";
        List<String> closestWords = List.of(
                "clear t/patient\n",
                "clear t/doctor\n",
                "clear t/activity\n");
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
                "add t/patient\n",
                "edit t/patient\n",
                "add t/doctor\n",
                "view t/patient\n",
                "list t/patient\n"
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
