package seedu.exceptions.nurseschedules;

import seedu.exceptions.HealthVaultException;

public class EmptyListException extends HealthVaultException {
    public String getMessage() {
        return "No schedules are found in the system!";
    }
}
