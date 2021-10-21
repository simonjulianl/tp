package gomedic.logic.commands;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalUserProfile.MAIN_PROFILE;
import static gomedic.testutil.TypicalUserProfile.OTHER_PROFILE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import gomedic.model.userprofile.UserProfile;
import gomedic.testutil.modelbuilder.UserProfileBuilder;

public class ProfileCommandTest {
    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new ProfileCommand(null, null));
    }

    @Test
    public void execute_updateProfile_updateSuccessful() throws Exception {
        UserProfile updatedProfile = new UserProfileBuilder()
                .withName("New name")
                .withDescription("new description")
                .build();

        ProfileCommandTest.ModelStubWithValidUpdate modelStub =
                new ProfileCommandTest.ModelStubWithValidUpdate(updatedProfile);

        CommandResult commandResult = new ProfileCommand(
                updatedProfile.getName(), updatedProfile.getDescription()
        ).execute(modelStub);

        assertEquals(String.format(
                ProfileCommand.MESSAGE_SUCCESS, updatedProfile),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ProfileCommand profileCommand = new ProfileCommand(
                MAIN_PROFILE.getName(),
                MAIN_PROFILE.getDescription()
        );

        ProfileCommand anotherProfileCommand = new ProfileCommand(
                OTHER_PROFILE.getName(),
                OTHER_PROFILE.getDescription()
        );

        // same object -> returns true
        assertEquals(profileCommand, profileCommand);

        // different types -> returns false
        assertNotEquals(1, profileCommand);

        // null -> returns false
        assertNotEquals(null, profileCommand);

        // different profiles -> returns false
        assertNotEquals(profileCommand, anotherProfileCommand);
    }


    /**
     * A Model stub that contains a valid profile update.
     */
    private static class ModelStubWithValidUpdate extends CommandTestUtil.ModelStub {
        private UserProfile userProfile;

        ModelStubWithValidUpdate(UserProfile userProfile) {
            requireNonNull(userProfile);
            this.userProfile = userProfile;
        }

        @Override
        public UserProfile getUserProfile() {
            return userProfile;
        }

        @Override
        public void setUserProfile(UserProfile userProfile) {
            this.userProfile = userProfile;
        }
    }
}
