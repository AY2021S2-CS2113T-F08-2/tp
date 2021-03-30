package seedu.exceptions.patient;

import seedu.exceptions.HealthVaultException;

public class InvalidIDLengthException extends HealthVaultException {

    public InvalidIDLengthException(String error) {
        this.error = error;
    }

    @Override
    public void getError(String input) {
        super.getError(input);
    }

}
