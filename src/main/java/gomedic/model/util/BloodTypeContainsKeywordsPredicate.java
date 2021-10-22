package gomedic.model.util;

import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.util.StringUtil;
import gomedic.model.person.patient.Patient;

/**
 * Tests that a Patient's blood type matches any of the keywords given.
 */
public class BloodTypeContainsKeywordsPredicate<T extends Patient> implements Predicate<T> {
    private final List<String> keywords;

    public BloodTypeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(T person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getBloodType().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof BloodTypeContainsKeywordsPredicate) {
            @SuppressWarnings("unchecked")
            BloodTypeContainsKeywordsPredicate<T> otherKeywords = (BloodTypeContainsKeywordsPredicate<T>) other;

            return keywords.equals(otherKeywords.keywords);
        }

        return false;
    }

}
