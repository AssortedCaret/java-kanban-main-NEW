package exception;

public class ManagerSaveException extends Exception {
    public ManagerSaveException() {
    }

    public ManagerSaveException(final String message) {
        super(message);
    }

    public ManagerSaveException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ManagerSaveException(final Throwable cause) {
        super(cause);
    }

}
