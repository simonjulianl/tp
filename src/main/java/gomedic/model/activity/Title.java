package gomedic.model.activity;

import gomedic.commons.util.AppUtil;

/**
 * Title represents a description of the activity.
 * Guarantees : immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title extends Description {
    public static final int MAX_CHAR = 60; // max char in title
    public static final String MESSAGE_CONSTRAINTS =
            "Description is at most " + MAX_CHAR + " chars including whitespaces";

    /**
     * Constructs an {@code Title}.
     *
     * @param text Description text.
     */
    public Title(String text) {
        super(text);
        AppUtil.checkArgument(isValidTitle(text), MESSAGE_CONSTRAINTS);
    }

    /**
     * @return true for text that has less than max_char and cannot be empty.
     */
    public static boolean isValidTitle(String text) {
        return text.length() <= MAX_CHAR && text.length() > 0;
    }
}
