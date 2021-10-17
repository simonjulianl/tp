package gomedic.logic.commands.findcommand;

import gomedic.logic.commands.Command;

import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_ALL;

/**
 * Finds and lists all entries of patients, doctors, and activities in GoMedic whose fields
 * contains any of the argument keywords in the corresponding fields.
 * Keyword matching is case insensitive.
 */
public class FindAllCommand {
    public static final String COMMAND_WORD = "find" + " " + PREFIX_TYPE_ALL;


}
