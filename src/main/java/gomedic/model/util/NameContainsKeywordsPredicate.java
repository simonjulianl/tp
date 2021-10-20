package gomedic.model.util;

import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.util.StringUtil;
import gomedic.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate<T extends Person> implements Predicate<T> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    // TODO: THE ORIGINAL AB3 IMPLEMENTATION CAN ONLY MATCH FULL STRINGS (E.G. "FIND JO" CANNOT FIND "JOHN SNOW" BUT
    //      "FIND JOHN" CAN. CHANGE IMPLEMENTATION SO THAT SUBSTRINGS CAN BE MATCHED AS WELL
    @Override
    public boolean test(T person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof NameContainsKeywordsPredicate) {
            @SuppressWarnings("unchecked")
            NameContainsKeywordsPredicate<T> otherKeywords = (NameContainsKeywordsPredicate<T>) other;

            return keywords.equals(otherKeywords.keywords);
        }

        return false;
    }

}
