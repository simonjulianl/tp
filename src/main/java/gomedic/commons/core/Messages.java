package gomedic.commons.core;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.text.similarity.LevenshteinDistance;

import javafx.util.Pair;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Sorry, %s is an invalid command.";
    public static final String MESSAGE_CONFLICTING_ACTIVITY =
            "Sorry, the activity's timing is conflicting with another activity";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ACTIVITY_ID = "The activity id doesn't exist in the list";
    public static final String MESSAGE_INVALID_DOCTOR_ID = "The doctor id doesn't exist in the list";
    public static final String MESSAGE_INVALID_PATIENT_ID = "The patient id doesn't exist in the list";
    public static final String MESSAGE_ITEMS_LISTED_OVERVIEW = "%1$d items listed!";
    public static final String MESSAGE_HELP_COMMANDS = generateHelpText();

    /**
     * Returns the 5 most similar commands calculated using Levenshtein Distance Algorithm.
     *
     * @param command the invalid command to be evaluated.
     * @return message containing similar commands to be displayed to the user.
     */
    public static String getSuggestions(String command) {

        // pool of command suggestions
        List<String> listOfCommands = Arrays.asList(
                "help",
                "add t/patient",
                "view t/patient",
                "delete t/patient",
                "edit t/patient",
                "list t/patient",
                "clear t/patient",
                "add t/doctor",
                "view t/doctor",
                "delete t/doctor",
                "edit t/doctor",
                "list t/doctor",
                "clear t/doctor",
                "find",
                "add t/appointment",
                "add t/activity",
                "delete t/activity",
                "list t/activity",
                "clear t/activity",
                "exit");
        // list of command targets
        List<String> listOfTargets = Arrays.asList("t/patient", "t/doctor", "t/activity", "t/appointment");
        // get hashset of types for checking
        HashSet<String> listOfTypes = new HashSet<>(listOfCommands.stream()
                .map(x -> x.split(" ")[0])
                .collect(Collectors.toList()));

        String[] commandArgs = command.split(" ", 2);
        List<String> approvedSuggestions = null;

        // if wrong command is too short, the command type is probably wrong
        if (commandArgs.length == 1) {
            approvedSuggestions = generateTypeSuggestions(command, listOfTypes, listOfTargets);
        // if wrong command has two parts, check both parts
        } else {
            List<String> approvedTypes = generateTypeSuggestions(commandArgs[0], listOfTypes, listOfTargets);
            List<String> approvedTargets = generateTargetSuggestions(commandArgs[1], listOfTypes, listOfTargets);
            HashSet<String> set1 = new HashSet<>(approvedTypes);
            HashSet<String> set2 = new HashSet<>(approvedTargets);
            set1.retainAll(set2);
            approvedSuggestions = new ArrayList<>(set1);
        }

        // if there are matches in the suggested items
        String reply = String.format(MESSAGE_UNKNOWN_COMMAND, command);
        Iterator<String> iterator = approvedSuggestions.iterator();
        if (iterator.hasNext()) {
            String additionalReply = " You can choose from these commands instead: \n";
            while (iterator.hasNext()) {
                additionalReply += iterator.next() + "    ";
            }
            reply += additionalReply;
        }

        return reply;
    }

    /**
     * Returns a list of string approved command suggestions based on type.
     *
     * @param command First part of erroneous command input.
     * @param listOfTypes Hashset of command types to refer to.
     * @param listOfTargets List of command targets to be appended to approved suggestions.
     * @return A list of approved suggestions.
     */
    private static List<String> generateTypeSuggestions(String command, HashSet<String> listOfTypes,
                                                    List<String> listOfTargets) {
        List<Pair<Integer, String>> closestStrings;
        LevenshteinDistance stringChecker = new LevenshteinDistance();
        HashSet<String> singleWordCommands = new HashSet<>(Arrays.asList("exit", "help"));

        // get a list of pairs of (levenshtein distance, type suggestion) sorted by distance
        closestStrings = listOfTypes.stream()
                .map(x -> new Pair<>(stringChecker.apply(x, command), x))
                .sorted(Comparator.comparingInt(Pair::getKey))
                .collect(Collectors.toList());
        // list of valid suggestions only if distance is less than or equal to half the suggested command
        List<Pair<Integer, String>> validTypes = closestStrings.stream()
                .filter(x -> x.getKey() <= Math.ceil(x.getValue().length() / 2))
                .collect(Collectors.toList());
        // append second part of command to available command types
        return validTypes.stream()
                .flatMap(x -> !singleWordCommands.contains(x.getValue())
                        ? listOfTargets
                        .stream()
                        .map(y -> {
                            if (Objects.equals(y, "t/appointment")) {
                                if (Objects.equals(x.getValue(), "add")) {
                                    return x.getValue() + " " + y;
                                }
                            }
                            return x.getValue() + " " + y;
                        })
                        : Stream.of(x.getValue()))
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of approved string command suggestions based on target.
     *
     * @param command Second part of erroneous input.
     * @param listOfTypes
     * @param listOfTargets
     * @return A list of command suggestions.
     */
    private static List<String> generateTargetSuggestions(String command, HashSet<String> listOfTypes,
                                                        List<String> listOfTargets) {
        List<Pair<Integer, String>> closestStrings;
        LevenshteinDistance stringChecker = new LevenshteinDistance();
        HashSet<String> singleWordCommands = new HashSet<>(Arrays.asList("exit", "help"));

        // get a list of pairs of (levenshtein distance, target suggestion) sorted by distance
        closestStrings = listOfTargets.stream()
                .map(x -> new Pair<>(stringChecker.apply(x, command), x))
                .sorted(Comparator.comparingInt(Pair::getKey))
                .collect(Collectors.toList());
        // list of valid suggestions only if distance is less than or equal to half the suggested command
        List<Pair<Integer, String>> validTargets = closestStrings.stream()
                .filter(x -> x.getKey() <= Math.ceil(x.getValue().length() / 2))
                .collect(Collectors.toList());
        // prepend first part of command to available command target
        return validTargets.stream()
                .flatMap(x -> x.getValue() != "t/appointment"
                        ? listOfTypes
                        .stream()
                        .map(y -> !singleWordCommands.contains(y)
                                ? y + " " + x.getValue()
                                : y)
                        : Stream.of("add t/appointment"))
                .collect(Collectors.toList());
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
                + "by the index number used in the displayed list.\n"
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
