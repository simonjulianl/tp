package gomedic.commons.core;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.LevenshteinDistance;

import javafx.util.Pair;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    /**
     * Returns the 5 most similar commands calculated using Levenshtein Distance Algorithm.
     *
     * @param command the invalid command to be evaluated.
     * @return message containing similar commands to be displayed to the user.
     */
    public static String getSuggestions(String command) {

        List<String> listOfCommands = Arrays.asList("add", "edit", "delete",
                "clear", "find", "list", "exit", "help");
        LevenshteinDistance stringChecker = new LevenshteinDistance();
        List<Pair<Integer, String>> closestStrings = listOfCommands.stream()
                .map(x -> new Pair<>(stringChecker.apply(x, command), x))
                .sorted(Comparator.comparingInt(Pair::getKey))
                .collect(Collectors.toList());

        String reply = String.format("Sorry, %s is an invalid command.", command);
        Iterator<Pair<Integer, String>> iterator = closestStrings.stream()
                .filter(x -> x.getKey() <= Math.ceil(x.getValue().length() / 2))
                .iterator();
        if (iterator.hasNext()) {
            String additionalReply = " You can choose from these commands instead: \n";
            while (iterator.hasNext()) {
                additionalReply += iterator.next().getValue() + "\n";
            }
            reply += additionalReply;
        }

        return reply;

    }
}
