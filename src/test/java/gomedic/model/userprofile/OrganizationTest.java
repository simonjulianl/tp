package gomedic.model.userprofile;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class OrganizationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Organization(null));
    }

    @Test
    public void constructor_invalidOrganization_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Organization(invalidName));
    }

    @Test
    public void isValidOrganization() {
        // null name
        assertThrows(NullPointerException.class, () -> Organization.isValidOrganizationName(null));

        // invalid name
        assertFalse(Organization.isValidOrganizationName("")); // empty string
        assertFalse(Organization.isValidOrganizationName(" ")); // spaces only
        assertFalse(Organization.isValidOrganizationName("^")); // only non-alphanumeric characters
        assertFalse(Organization.isValidOrganizationName("NUH*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Organization.isValidOrganizationName("nuh")); // alphabets only
        assertTrue(Organization.isValidOrganizationName("12345")); // numbers only
        assertTrue(Organization.isValidOrganizationName("national hospital 2")); // alphanumeric characters
        assertTrue(Organization.isValidOrganizationName("NUH")); // with capital letters
    }

    @Test
    public void toString_sameOrganizationName_testPassed() {
        String organizationName = "nuh";
        Organization organization = new Organization(organizationName);
        assertEquals(organization.toString(), organizationName);
    }

    @Test
    public void equals_differentInstanceSameName_isEqual() {
        String organizationName = "nuh";
        Organization organization = new Organization(organizationName);
        Organization otherOrganization = new Organization(organizationName);
        assertEquals(otherOrganization, organization);
    }
}
