package gomedic.model.userprofile;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gomedic.model.activity.Description;
import gomedic.model.commonfield.Name;

class UserProfileTest {
    private static Name name;
    private static Description description;
    private static UserProfile userProfile;

    @BeforeAll
    public static void setUp() {
        name = new Name("User profile not set yet");
        description = new Description("Refer to profile command to set profile description");

        userProfile = new UserProfile(name, description);
    }

    @Test
    void constructor_anyNull_throwsNullArgumentException() {
        assertThrows(NullPointerException.class, () -> new UserProfile(null, null));
    }

    @Test
    void getName() {
        assertEquals(name, userProfile.getName());
    }

    @Test
    void getDescription() {
        assertEquals(description, userProfile.getDescription());
    }

    @Test
    void testHashCode() {
        int hash = Objects.hash(name, description);
        assertEquals(hash, userProfile.hashCode());
    }

    @Test
    void testEquals() {
        Name diffName = new Name("Johnny");
        Description diffDescription = new Description("different description");

        UserProfile userDiffName = new UserProfile(diffName, description);
        UserProfile userDiffDescription = new UserProfile(name, diffDescription);

        assertNotEquals(userProfile, userDiffName); // Profile is not same if name is not same
        assertNotEquals(userProfile, userDiffDescription); // Profile is not same if description is not same

        // Same if other person is different instance with the same id and data fields
        assertEquals(userProfile, new UserProfile(name, description));
    }

    @Test
    void testToString() {
        assertEquals("Name: User profile not set yet;"
                + " Description: Refer to profile command to set profile description", userProfile.toString());
    }
}
