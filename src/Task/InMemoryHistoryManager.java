package Task;

import java.util.Arrays;

public class InMemoryHistoryManager implements HistoryManager {
    @Override
    public void add(Task task) {

    }

    @Override
    public String getHistory() {
        Integer [] mass = new Integer[10];
        for(int i = 0; i < InMemoryTaskManager.historyList.size(); i++) {
            if (i == 10)
                break;
            else
                mass[i] = InMemoryTaskManager.historyList.get(i);
        }
        return Arrays.toString(mass);
    }
}
