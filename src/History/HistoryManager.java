package History;

import Task.Task;
import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);
    ArrayList getHistory();
}
