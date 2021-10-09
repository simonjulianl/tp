package gomedic.model.activity;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import gomedic.commons.util.AppUtil;

/**
 * Represents an Activity description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {
    public static final int MAX_CHAR = 500; // max char in description
    public static final String MESSAGE_CONSTRAINTS =
            "Description is at most " + MAX_CHAR + " chars including whitespaces";

    private final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param text Description text.
     */
    public Description(String text) {
        requireNonNull(text);
        AppUtil.checkArgument(isValidDescription(text), MESSAGE_CONSTRAINTS);
        value = text;
    }

    /**
     * @return true for text that has less than max_char
     */
    public static boolean isValidDescription(String text) {
        return text.length() <= MAX_CHAR;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Check whether the description contains one of the terms (case-insensitive).
     *
     * @param terms varargs String.
     * @return true if text contains term.
     */
    public boolean contains(String... terms) {
        return Arrays
                .stream(terms)
                .anyMatch(it -> value.toLowerCase().contains(it.toLowerCase()));
    }
}
