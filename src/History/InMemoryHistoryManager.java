package History;

import Task.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private ArrayList<Task> historyTaskList = new ArrayList<>();
    @Override
    public void add(Task task) {
        historyTaskList.add(task);
        if(historyTaskList.size() > 10)
           historyTaskList.remove(0);
    }

    @Override
    public ArrayList getHistory() {
        return historyTaskList;
    }
}
