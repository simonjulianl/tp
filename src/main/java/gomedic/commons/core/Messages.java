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

    public static final String MESSAGE_UNKNOWN_COMMAND = "Sorry, %s is an invalid command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_ACTIVITY_ID = "The activity id doesn't exist in the list";
    public static final String MESSAGE_INVALID_DOCTOR_ID = "The doctor id doesn't exist in the list";
    public static final String MESSAGE_INVALID_PATIENT_ID = "The patient id doesn't exist in the list";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_HELP_COMMANDS = generateHelpText();

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

        String reply = String.format(MESSAGE_UNKNOWN_COMMAND, command);
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

    /**
     * Returns a summary of what each command does in String format to be passed to JavaFX.
     *
     * @return a string of command descriptions.
     */
    private static String generateHelpText() {
        String addDescription = "add:\n   Adds a patient, doctor or activity to the address book.\n\n";
        String clearDescription = "clear:\n  Empties all data in GoMedic.\n\n";
        String deleteDescription = "delete:\n    Deletes the patient, doctor or activity identified "
                + "by the index number used in their respective list.\n\n";
        String editDescription = "edit:\n    Edits the details of the patient, doctor or activity identified "
                + "by the index number used in the displayed person list.\n"
                + "    Existing values will be overwritten by the input values.\n\n";
        String exitDescription = "exit:\n    Exits GoMedic and closes the window.\n\n";
        String findDescription = "find:\n    Finds entries that contain the given keyword as substring "
                + "in their entry attributes.\n\n";
        String listDescription = "list:\n    List all patients, doctors or activities "
                + "as specified by the user.\n\n";
        String helpDescription = "help:\n    Returns a list of commands and a "
                + "brief description on what they do.\n\n";

        return addDescription + clearDescription + deleteDescription + editDescription + exitDescription
                + findDescription + listDescription + helpDescription;

    }
}
