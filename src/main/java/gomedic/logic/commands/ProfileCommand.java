package gomedic.logic.commands;

import static gomedic.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static gomedic.logic.parser.CliSyntax.PREFIX_NAME;
import static gomedic.logic.parser.CliSyntax.PREFIX_ORGANIZATION;
import static gomedic.logic.parser.CliSyntax.PREFIX_POSITION;
import static java.util.Objects.requireNonNull;

import gomedic.commons.util.CollectionUtil;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.commonfield.Name;
import gomedic.model.person.doctor.Department;
import gomedic.model.userprofile.Organization;
import gomedic.model.userprofile.Position;
import gomedic.model.userprofile.UserProfile;

/**
 * Updates the user profile in GoMedic.
 */
public class ProfileCommand extends Command {
    public static final String COMMAND_WORD = "profile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the user's profile in GoMedic. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_POSITION + "POSITION "
            + PREFIX_DEPARTMENT + "DEPARTMENT "
            + PREFIX_ORGANIZATION + "ORGANIZATION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Smith "
            + PREFIX_POSITION + "Senior Resident "
            + PREFIX_DEPARTMENT + "Cardiology "
            + PREFIX_ORGANIZATION + "NUH";

    public static final String MESSAGE_SUCCESS = "Profile updated to:\n %1$s";

    private final Name name;
    private final Position position;
    private final Department department;
    private final Organization organization;

    /**
     * Creates an ProfileCommand to update the {@code UserProfile}.
     */
    public ProfileCommand(Name name, Position position, Department department, Organization organization) {
        CollectionUtil.requireAllNonNull(name, organization, position, department);
        this.name = name;
        this.organization = organization;
        this.position = position;
        this.department = department;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UserProfile replacementUserProfile = new UserProfile(name, position, department, organization);

        model.setUserProfile(replacementUserProfile);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, replacementUserProfile));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfileCommand // instanceof handles nulls
                && name.equals(((ProfileCommand) other).name)
                && position.equals(((ProfileCommand) other).position)
                && department.equals(((ProfileCommand) other).department)
                && organization.equals(((ProfileCommand) other).organization));
    }
}
