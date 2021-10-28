package gomedic.commons.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
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

    // pool of valid command suggestions for reference
    private static final List<String> listOfCommands = Arrays.asList(
            "help",
            "referral",
            "profile",
            "add t/patient",
            "view t/patient",
            "delete t/patient",
            "edit t/patient",
            "list t/patient",
            "clear t/patient",
            "find t/patient",
            "add t/doctor",
            "delete t/doctor",
            "edit t/doctor",
            "list t/doctor",
            "clear t/doctor",
            "find t/doctor",
            "add t/appointment",
            "add t/activity",
            "delete t/activity",
            "list t/activity",
            "clear t/activity",
            "find t/activity",
            "exit");
    // list of command targets
    private static final List<String> listOfTargets = Arrays.asList("t/patient", "t/doctor",
            "t/activity", "t/appointment");
    // get hashset of types for checking
    private static final HashSet<String> listOfTypes = new HashSet<>(listOfCommands.stream()
            .map(x -> x.split(" ")[0])
            .collect(Collectors.toList()));
    private static final HashSet<String> singleWordCommands = new HashSet<>(Arrays.asList("exit", "help",
            "profile", "referral"));


    /**
     * Returns the 5 most similar commands calculated using Levenshtein Distance Algorithm.
     *
     * @param command the invalid command to be evaluated.
     * @return message containing similar commands to be displayed to the user.
     */
    public static String getSuggestions(String command) {

        String[] commandArgs = command.split(" ", 2);
        List<String> approvedSuggestions;

        // if wrong command is too short, the command type is probably wrong
        if (commandArgs.length == 1) {
            approvedSuggestions = generateTypeSuggestions(command);
            // if wrong command has two parts, check both parts
        } else {
            List<String> approvedTypes = generateTypeSuggestions(commandArgs[0]);
            List<String> approvedTargets = generateTargetSuggestions(commandArgs[1]);
            HashSet<String> set1 = new HashSet<>(approvedTypes);
            HashSet<String> set2 = new HashSet<>(approvedTargets);
            set1.retainAll(set2);
            approvedSuggestions = new ArrayList<>(set1);
        }

        // if there are matches in the suggested items
        String reply = String.format(MESSAGE_UNKNOWN_COMMAND, command);
        if (approvedSuggestions.stream().anyMatch(x -> x.contains("clear"))) {
            approvedSuggestions.add("clear");
        }
        Iterator<String> iterator = approvedSuggestions.stream().limit(5).iterator();
        if (iterator.hasNext()) {
            StringBuilder additionalReply = new StringBuilder(" You can choose from these commands instead: \n");
            while (iterator.hasNext()) {
                String nextCommand = iterator.next();
                additionalReply.append(nextCommand).append("    ");
            }
            reply += additionalReply;
        }

        return reply;
    }

    /**
     * Returns a list of string approved command suggestions based on type.
     *
     * @param command First part of erroneous command input.
     * @return A list of approved suggestions.
     */
    private static List<String> generateTypeSuggestions(String command) {
        List<Pair<Integer, String>> closestStrings;
        LevenshteinDistance stringChecker = new LevenshteinDistance();

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
                                } else {
                                    return null;
                                }
                            } else if (Objects.equals(x.getValue(), "view")) {
                                if (Objects.equals(y, "t/patient")) {
                                    return x.getValue() + " " + y;
                                } else {
                                    return null;
                                }
                            } else {
                                return x.getValue() + " " + y;
                            }
                        })
                        : Stream.of(x.getValue()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of approved string command suggestions based on target.
     *
     * @param command Second part of erroneous input.
     * @return A list of command suggestions.
     */
    private static List<String> generateTargetSuggestions(String command) {
        List<Pair<Integer, String>> closestStrings;
        LevenshteinDistance stringChecker = new LevenshteinDistance();

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
                .flatMap(x -> !Objects.equals(x.getValue(), "t/appointment")
                        ? listOfTypes
                        .stream()
                        .map(y -> {
                            if (singleWordCommands.contains(y)) {
                                return y;
                            } else if (Objects.equals(y, "view")) {
                                if (Objects.equals(x.getValue(), "t/patient")) {
                                    return y + " " + x.getValue();
                                } else {
                                    return null;
                                }
                            }
                            return y + " " + x.getValue();
                        })
                        : Stream.of("add t/appointment"))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Returns a summary of what each command does in String format to be passed to JavaFX.
     *
     * @return a string of command descriptions.
     */
    public static String generateHelpText() {

        String addTemplate = "add %s:\n   Adds a %s to the address book.\n\n";
        String clearTemplate = "clear %s:\n  Empties all %ss in GoMedic.\n\n";
        String deleteTemplate = "delete %s:\n    Deletes the %s identified "
                + "by the index number used in their respective list.\n\n";
        String editTemplate = "edit %s:\n    Edits the details of the %ss identified "
                + "by the index number used in the displayed list.\n"
                + "    Existing values will be overwritten by the input values.\n\n";
        String findTemplate = "find %s:\n    Finds entries in %ss that contain the given keyword as substring "
                + "in their entry attributes.\n\n";
        String listTemplate = "list %s:\n    List all %s as specified by the user.\n\n";

        String addDescription = listOfTargets.stream()
                .map(target -> helpStringBuilder(addTemplate, target))
                .collect(Collectors.joining());
        String clearDescription = "clear:\n Empties all data in GoMedic.\n\n";
        clearDescription += listOfTargets.stream()
                .map(target -> !target.equals("t/appointment")
                        ? helpStringBuilder(clearTemplate, target)
                        : "")
                .collect(Collectors.joining());
        String deleteDescription = listOfTargets.stream()
                .map(target -> !target.equals("t/appointment")
                        ? helpStringBuilder(deleteTemplate, target)
                        : "")
                .collect(Collectors.joining());
        String editDescription = listOfTargets.stream()
                .map(target -> !target.equals("t/appointment")
                        ? helpStringBuilder(editTemplate, target)
                        : "")
                .collect(Collectors.joining());
        String exitDescription = "exit:\n    Exits GoMedic and closes the window.\n\n";
        String findDescription = "find:\n    Finds entries in GoMedic that contain the given keyword as substring "
                + "in their entry attributes.\n\n";
        findDescription += listOfTargets.stream()
                .map(target -> !target.equals("t/appointment")
                        ? helpStringBuilder(findTemplate, target)
                        : "")
                .collect(Collectors.joining());
        String listDescription = "list:\n    List all patients, doctors or activities "
                + "as specified by the user.\n\n";
        listDescription += listOfTargets.stream()
                .map(target -> !target.equals("t/appointment")
                        ? helpStringBuilder(listTemplate, target)
                        : "")
                .collect(Collectors.joining());
        String helpDescription = "help:\n    Returns a list of commands and a "
                + "brief description on what they do.\n\n";
        String referralDescription = "referral:\n    Generates a pdf referral for a patient.\n\n";
        String profileDescription = "profile:\n    Helps to set the user's profile in GoMedic.\n\n";
        String viewDescription = "view t/patient:\n    Views all the details of a specific patient in one page.\n\n";

        return addDescription + clearDescription + deleteDescription + editDescription
                + findDescription + listDescription + profileDescription + viewDescription
                + referralDescription + helpDescription + exitDescription;

    }

    /**
     * Returns a filled-in help template with appropriate types and targets.
     *
     * @param commandTemplate String help template.
     * @param commandTarget Command target to be filled in.
     * @return Completed String help template.
     */
    private static String helpStringBuilder(String commandTemplate, String commandTarget) {
        String cleanCommandTarget = commandTarget.endsWith("y")
                ? commandTarget.substring(2).replace("y", "ie")
                : commandTarget.substring(2);
        return String.format(commandTemplate, commandTarget, cleanCommandTarget);
    }
}
