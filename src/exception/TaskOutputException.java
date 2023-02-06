package exception;

/**
 * класс используется для проверки пересечения времени, в class FileBackedTasksManager, метод intersectionCheck(task);
 */
public class TaskOutputException extends  Exception{
    public TaskOutputException() {
    }

    public TaskOutputException(final String message) {
        super(message);
    }
}
