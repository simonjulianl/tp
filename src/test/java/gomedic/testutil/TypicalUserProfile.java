package gomedic.testutil;

import gomedic.model.userprofile.UserProfile;
import gomedic.testutil.modelbuilder.UserProfileBuilder;

public class TypicalUserProfile {
    public static final UserProfile MAIN_PROFILE = new UserProfileBuilder().build();
    public static final UserProfile OTHER_PROFILE = new UserProfileBuilder()
            .withName("new name")
            .withPosition("new position")
            .withDepartment("new department")
            .withOrganization("new organization")
            .build();
}
