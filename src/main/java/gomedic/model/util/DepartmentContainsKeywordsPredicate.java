package gomedic.model.util;

import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.util.StringUtil;
import gomedic.model.person.doctor.Doctor;

/**
 * Tests that a Doctor's department matches any of the keywords given.
 */
public class DepartmentContainsKeywordsPredicate<T extends Doctor> implements Predicate<T> {
    private final List<String> keywords;

    public DepartmentContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(T person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getDepartment().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof DepartmentContainsKeywordsPredicate) {
            @SuppressWarnings("unchecked")
            DepartmentContainsKeywordsPredicate<T> otherKeywords = (DepartmentContainsKeywordsPredicate<T>) other;

            return keywords.equals(otherKeywords.keywords);
        }

        return false;
    }

}
