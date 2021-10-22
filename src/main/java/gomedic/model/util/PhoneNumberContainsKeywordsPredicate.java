package gomedic.model.util;

import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.util.StringUtil;
import gomedic.model.person.Person;

/**
 * Tests that a Patient's or Doctor's phone number matches any of the keywords given.
 */
public class PhoneNumberContainsKeywordsPredicate<T extends Person> implements Predicate<T> {
    private final List<String> keywords;

    public PhoneNumberContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(T person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof PhoneNumberContainsKeywordsPredicate) {
            @SuppressWarnings("unchecked")
            PhoneNumberContainsKeywordsPredicate<T> otherKeywords = (PhoneNumberContainsKeywordsPredicate<T>) other;

            return keywords.equals(otherKeywords.keywords);
        }

        return false;
    }

}
