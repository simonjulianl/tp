package gomedic.logic.commands;

import static gomedic.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static gomedic.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import gomedic.commons.util.CollectionUtil;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.activity.Description;
import gomedic.model.commonfield.Name;
import gomedic.model.userprofile.UserProfile;

/**
 * Updates the user profile in GoMedic.
 */
public class ProfileCommand extends Command {
    public static final String COMMAND_WORD = "profile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the user's profile in GoMedic. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Smith "
            + PREFIX_DESCRIPTION + "This is my personal tracker for all work related activities and contacts";

    public static final String MESSAGE_SUCCESS = "Profile updated to:\n %1$s";

    private final Name name;
    private final Description description;

    /**
     * Creates an ProfileCommand to update the {@code UserProfile}.
     */
    public ProfileCommand(Name name, Description description) {
        CollectionUtil.requireAllNonNull(name, description);
        this.name = name;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UserProfile oldUserProfile = model.getUserProfile();
        UserProfile replacementUserProfile = new UserProfile(name, description);

        model.setUserProfile(replacementUserProfile);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, replacementUserProfile));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfileCommand // instanceof handles nulls
                && name.equals(((ProfileCommand) other).name)
                && description.equals(((ProfileCommand) other).description));
    }
}
