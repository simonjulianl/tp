package gomedic.model.userprofile;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gomedic.model.commonfield.Name;
import gomedic.model.person.doctor.Department;

class UserProfileTest {
    private static Name name;
    private static Position position;
    private static Department department;
    private static Organization organization;
    private static UserProfile userProfile;

    @BeforeAll
    public static void setUp() {
        name = new Name("User profile name not set yet");
        position = new Position("User profile position not set yet");
        department = new Department("User profile department not set yet");
        organization = new Organization("User profile organization not set yet");

        userProfile = new UserProfile(name, position, department, organization);
    }

    @Test
    void constructor_anyNull_throwsNullArgumentException() {
        assertThrows(NullPointerException.class, () -> new UserProfile(null, null, null, null));
    }

    @Test
    void getName() {
        assertEquals(name, userProfile.getName());
    }

    @Test
    void getPosition() {
        assertEquals(position, userProfile.getPosition());
    }

    @Test
    void getDepartment() {
        assertEquals(department, userProfile.getDepartment());
    }

    @Test
    void getOrganization() {
        assertEquals(organization, userProfile.getOrganization());
    }

    @Test
    void testHashCode() {
        int hash = Objects.hash(name, position, department, organization);
        assertEquals(hash, userProfile.hashCode());
    }

    @Test
    void testEquals() {
        Name diffName = new Name("Johnny");
        Position diffPosition = new Position("different position");
        Department diffDepartment = new Department("different department");
        Organization diffOrganization = new Organization("different organization");

        UserProfile userDiffName = new UserProfile(diffName, position, department, organization);
        UserProfile userDiffPosition = new UserProfile(name, diffPosition, department, organization);
        UserProfile userDiffDepartment = new UserProfile(name, position, diffDepartment, organization);
        UserProfile userDiffOrganization = new UserProfile(name, position, department, diffOrganization);

        assertNotEquals(userProfile, userDiffName); // Profile is not same if name is not same
        assertNotEquals(userProfile, userDiffPosition); // Profile is not same if position is not same
        assertNotEquals(userProfile, userDiffDepartment); // Profile is not same if department is not same
        assertNotEquals(userProfile, userDiffOrganization); // Profile is not same if organization is not same

        // Same if other user profile is different instance with the same data fields
        assertEquals(userProfile, new UserProfile(name, position, department, organization));
    }

    @Test
    void testToString() {
        assertEquals("Name: User profile name not set yet;"
                + " Position: User profile position not set yet;"
                + " Department: User profile department not set yet;"
                + " Organization: User profile organization not set yet", userProfile.toString());
    }
}
