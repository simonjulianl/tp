package gomedic.logic.commands.editcommand;

import static gomedic.logic.commands.CommandTestUtil.DESC_MEETING;
import static gomedic.logic.commands.CommandTestUtil.DESC_PAPER_REVIEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import gomedic.testutil.editdescriptorbuilder.EditActivityDescriptorBuilder;

public class EditActivityDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditActivityCommand.EditActivityDescriptor descriptorWithSameValues =
                new EditActivityCommand.EditActivityDescriptor(DESC_MEETING);
        assertEquals(descriptorWithSameValues, DESC_MEETING);

        // same object -> returns true
        assertEquals(DESC_MEETING, DESC_MEETING);

        // null -> returns false
        assertNotEquals(DESC_MEETING, null);

        // different types -> returns false
        assertNotEquals(DESC_MEETING, 5);

        // different values -> returns false
        assertNotEquals(DESC_MEETING, DESC_PAPER_REVIEW);

        // different field -> returns false
        EditActivityCommand.EditActivityDescriptor editedMainDoctor =
                new EditActivityDescriptorBuilder(DESC_MEETING)
                        .withTitle("another title")
                        .build();
        assertNotEquals(editedMainDoctor, DESC_MEETING);
    }
}
