package gomedic.ui;

import java.util.logging.Logger;

import gomedic.commons.core.GuiSettings;
import gomedic.commons.core.LogsCenter;
import gomedic.logic.Logic;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.ui.table.ActivityTable;
import gomedic.ui.table.DoctorTable;
import gomedic.ui.table.PatientTable;
import gomedic.ui.view.PatientView;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final Logic logic;
    private final HelpWindow helpWindow;

    // Independent Ui parts residing in this Ui container
    private ActivityTable activityTable;
    private DoctorTable doctorTable;
    private PatientTable patientTable;
    private PatientView patientView;

    private ResultDisplay resultDisplay;
    private SideWindow sideWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    // Important Region where we can swap the root to change the UI.
    @FXML
    private StackPane modelListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane sideWindowPlaceholder;

    @FXML
    private HBox mainWindow;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        // Value to indicate what model is currently being shown.
        // 0 -> Activity, 1 -> Activity by Start Time, 2 -> Doctor, 3 -> Patient
        ObservableValue<Integer> modelItemBeingShown = logic.getModelBeingShown();
        modelItemBeingShown.addListener((obs, oldVal, newVal) -> {
            modelListPanelPlaceholder.getChildren().clear();
            switch (newVal) {
            case 0:
                activityTable = new ActivityTable(logic.getFilteredActivityListById());
                modelListPanelPlaceholder.getChildren().add(activityTable.getRoot());
                break;
            case 1:
                activityTable = new ActivityTable(logic.getFilteredActivityListByStartTime());
                modelListPanelPlaceholder.getChildren().add(activityTable.getRoot());
                break;
            case 2:
                modelListPanelPlaceholder.getChildren().add(doctorTable.getRoot());
                break;
            case 3:
                modelListPanelPlaceholder.getChildren().add(patientTable.getRoot());
                break;
            case 4:
                modelListPanelPlaceholder.getChildren().add(patientView.getRoot());
                break;
            default:
                // do nothing
                break;
            }
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        activityTable = new ActivityTable(logic.getFilteredActivityListById());
        doctorTable = new DoctorTable(logic.getFilteredDoctorList());
        patientTable = new PatientTable(logic.getFilteredPatientList());
        patientView = new PatientView(logic.getViewPatient());

        // fill in the side window
        sideWindow = new SideWindow(logic.getModelBeingShown());
        sideWindowPlaceholder.getChildren().add(sideWindow.getRoot());

        // by default, show the activity first
        modelListPanelPlaceholder.getChildren().add(activityTable.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookRootFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        //  set full-screen if wanted
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public ActivityTable getActivityTable() {
        return activityTable;
    }

    public DoctorTable getDoctorTable() {
        return doctorTable;
    }

    public PatientTable getPatientTable() {
        return patientTable;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + e.getMessage());
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
