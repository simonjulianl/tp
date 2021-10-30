package gomedic.ui;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



public class CommandHistoryTest {

    private static final String TEST_COMMAND_STRING_1 = "test 1";
    private static final String TEST_COMMAND_STRING_2 = "test 2";
    private final CommandHistory commandHistoryInstance = new CommandHistory();

    @Test
    public void getPreviousCommand_validHistory_success() {
        commandHistoryInstance.addToHistory(TEST_COMMAND_STRING_1);
        assertEquals(TEST_COMMAND_STRING_1, commandHistoryInstance.getPreviousCommand());
    }

    @Test
    public void getNextCommand_validHistory_success() {
        addCommands();
        assertEquals(TEST_COMMAND_STRING_2, commandHistoryInstance.getPreviousCommand());
    }

    @Test
    public void getCommands_emptyCommand_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, commandHistoryInstance::getNextCommand);
        assertThrows(IndexOutOfBoundsException.class, commandHistoryInstance::getPreviousCommand);
    }

    @Test
    public void ifHasNextCommand_hasCommands_success() {
        addCommands();
        commandHistoryInstance.getPreviousCommand(); // no next if there is only 1 command
        assertFalse(commandHistoryInstance.hasNextCommand());
    }

    @Test
    public void ifHasPreviousCommand_hasCommands_success() {
        addCommands();
        assertTrue(commandHistoryInstance.hasPreviousCommand());
    }

    private void addCommands() {
        commandHistoryInstance.addToHistory(TEST_COMMAND_STRING_1);
        commandHistoryInstance.addToHistory(TEST_COMMAND_STRING_2);
    }
}
