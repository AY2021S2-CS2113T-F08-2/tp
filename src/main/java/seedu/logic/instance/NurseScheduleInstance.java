package seedu.logic.instance;

import seedu.logic.parser.NurseSchedulesParser;
import seedu.model.NurseSchedule;
import seedu.storage.NurseScheduleStorage;
import seedu.ui.NurseScheduleUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Main entry-point for the NurseSchedules instance.
 */
public class NurseScheduleInstance {

    private NurseSchedulesParser parser;
    private NurseScheduleStorage storage;

    /** The list of nurse schedules. */
    List<NurseSchedule> nurseSchedules = new ArrayList<NurseSchedule>();

    public static void main() {
        new NurseScheduleInstance().run();
    }

    /** Runs the program until termination. */
    private void run() {
        initClasses();
        start();
        runCommandLoopUntilExit();
    }

    public void initClasses() {
        this.parser = new NurseSchedulesParser();
        this.storage = new NurseScheduleStorage();
    }

    private void start() {
        storage.load(nurseSchedules);
        NurseScheduleUI.printNurseScheduleWelcomeMessage();
    }

    /** Reads the user command and executes it, until the user issues the exit command. */
    private void runCommandLoopUntilExit() {
        boolean isRun = true;
        while (isRun) {
            NurseScheduleUI.nurseSchedulePrompt();
            String line = parser.getUserInput().trim();
            String command = parser.getFirstWord(line);
            isRun = parser.commandHandler(nurseSchedules, command, line);
        }
    }
}
