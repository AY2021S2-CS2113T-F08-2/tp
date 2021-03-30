package seedu.logic.command;

import seedu.exceptions.nurseschedules.EmptyListException;
import seedu.exceptions.nurseschedules.NurseIdNotFound;
import seedu.logic.parser.NurseSchedulesParser;
import seedu.model.NurseSchedule;
import seedu.ui.NurseScheduleUI;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NurseScheduleActions {

    List<NurseSchedule> findSchedules = new ArrayList<NurseSchedule>();
    List<String> nursesFound = new ArrayList<String>();
    private String nurseID = null;

    private static ArrayList<NurseSchedule> nurseSchedules = new ArrayList<>();

    public NurseScheduleActions(ArrayList<NurseSchedule> load) {
        nurseSchedules = load;
    }

    public void clearSchedules() {
        nurseSchedules.clear();
    }

//    public void addSchedule(List<NurseSchedule> nurseSchedules, String[] details) throws ParseException {
//        if (NurseSchedulesParser.isValidDate(details[2])) {
//            nurseSchedules.add(new NurseSchedule(details[0], details[1], details[2]));
//            NurseScheduleUI.printAddedSchedule(details[1], details[2]);
//        }
//    }

    public void addSchedule(String[] details) {
        try {
            nurseSchedules.add(new NurseSchedule(details[0], details[1], details[2]));
            NurseScheduleUI.printAddedSchedule(details[1], details[2]);
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handler when list command is issued.
     *
     * @param details Contains either NurseID or 'all'
     */
    public void listSchedules(String[] details)
            throws EmptyListException, NurseIdNotFound {
        if (details[0].equals("ALL")) {
            listAllSchedules();
        } else if (isValidNurseID(nurseSchedules, details[0])) {
            findSchedules.clear();
            getNurseSchedulesByID(nurseSchedules, details[0]);
            printSchedules(findSchedules);
        }
    }

    /**
     * Handler for 'list all' schedules.
     *
     * @throws EmptyListException when nurse schedule list is empty
     */
    public void listAllSchedules() throws EmptyListException {
        nursesFound.clear();
        if (nurseSchedules.size() == 0) {
            throw new EmptyListException();
        } else {
            for (int i = 0; i < nurseSchedules.size(); i++) {
                findSchedules.clear();
                if (!isNurseDone(nurseSchedules, i)) {
                    getNurseSchedulesByID(nurseSchedules, nurseID);
                    printSchedules(findSchedules);
                }
            }
        }
    }

    /**
     * Deletes schedule with given nurseID and datetime.
     *
     * @param details nurseID to delete
     */
    public void deleteSchedule(String[] details) throws NurseIdNotFound {
        int i = 0;
        while (i < nurseSchedules.size()) {
            if(!isValidNurseID(nurseSchedules, details[0])) {
                break;
            }
            if ((nurseSchedules.get(i).getNurseID()).equals(details[0])
                    && nurseSchedules.get(i).getDatetime().equals(details[1])) {
                NurseScheduleUI.printDeletedSchedule(nurseSchedules.get(i).getPatientID(),
                        nurseSchedules.get(i).getFormattedDatetime());
                nurseSchedules.remove(i);
                break;
            }
            i++;
        }
    }

    private void getNurseSchedulesByID(List<NurseSchedule> nurseSchedules, String id) {
        int i = 0;
        while (i < nurseSchedules.size()) {
            if (nurseSchedules.get(i).getNurseID().equals(id)) {
                findSchedules.add(nurseSchedules.get(i));
            }
            i++;
        }
        Collections.sort(findSchedules);
        //System.out.println(UI.prettyPrint(id, 10));
        System.out.println(id);
    }

    /**
     * Prints schedules.
     *
     * @param list List of schedules to be printed
     */
    private void printSchedules(List<NurseSchedule> list) {
        int i = 0;
        while (i < list.size()) {
            System.out.println("\t" + list.get(i).toFind());
            i++;
        }
    }

    private boolean isNurseDone(List<NurseSchedule> nurseSchedules, int i) {
        if (nursesFound.contains(nurseSchedules.get(i).getNurseID())) {
            return true;
        } else {
            nursesFound.add(nurseSchedules.get(i).getNurseID());
            nurseID = nurseSchedules.get(i).getNurseID();
            return false;
        }
    }

    /**
     * Checks if nurseID exists within schedules.
     *
     * @param nurseSchedules List of all schedules
     * @param id NurseID to check
     * @return boolean
     * @throws NurseIdNotFound if id does not exist
     */
    private boolean isValidNurseID(List<NurseSchedule> nurseSchedules, String id) throws NurseIdNotFound {
        int i = 0;
        while (i < nurseSchedules.size()) {
            if (nurseSchedules.get(i).getNurseID().equals(id)) {
                return true;
            }
            i++;
        }
        throw new NurseIdNotFound();
    }

    public int getSize() {
        return nurseSchedules.size();
    }

    public String toSaveFile(int i) {
        return nurseSchedules.get(i).toSave();
    }
}
