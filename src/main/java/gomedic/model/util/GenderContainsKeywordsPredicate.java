package gomedic.model.util;

import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.util.StringUtil;
import gomedic.model.person.patient.Patient;

/**
 * Tests that a Patient's gender matches any of the keywords given.
 */
public class GenderContainsKeywordsPredicate<T extends Patient> implements Predicate<T> {
    private final List<String> keywords;

    public GenderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(T person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getGender().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof GenderContainsKeywordsPredicate) {
            @SuppressWarnings("unchecked")
            GenderContainsKeywordsPredicate<T> otherKeywords = (GenderContainsKeywordsPredicate<T>) other;

            return keywords.equals(otherKeywords.keywords);
        }

        return false;
    }

}
