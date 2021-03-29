package seedu.storage;

import seedu.model.staff.Staff;
import seedu.logic.command.StaffActions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class StaffStorage {
    private static final String FILE_PATH = "data/Staff.txt";

    public static void fileHandling() {
        try {
            loadFile();
        } catch (FileNotFoundException e) {
            createFile();
        }
    }

    public static void loadTask(String line) {
        String[] arr = line.split("\\|");
        Staff staff = new Staff(arr);
        StaffActions.addStaff(staff);
    }

    public static void loadFile() throws FileNotFoundException {
        File f = new File(FILE_PATH);           // create a File for the given file path
        Scanner s = new Scanner(f);            // create a Scanner using the File as the source
        while (s.hasNext()) {
            loadTask(s.nextLine());
        }
    }

    public static void writeToFile() throws IOException {
        createFile();
        FileWriter fw = new FileWriter(FILE_PATH);
        for (int i = 0; i < StaffActions.getNumStaff(); i++) {
            ArrayList<Staff> buffer = StaffActions.getList();
            fw.write(formWriteData(buffer.get(i)));
        }
        fw.close();
    }

    public static void createFile() {
        try {
            File myObj = new File(FILE_PATH);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String formWriteData(Staff staff) {
        return (staff.getId() + " | " + staff.getName() + " | " + staff.getAge() + " | " + staff.getSpecialisation() + "\n");
    }
}
