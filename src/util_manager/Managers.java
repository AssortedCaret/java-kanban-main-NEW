package util_manager;

import history.HistoryManager;
import history.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import manager.FileBackedTasksManager;
import manager.TaskManager;
import server.HttpTaskManager;

import java.io.IOException;

public class Managers {

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
    public static TaskManager getDefaultFile(){
        return new FileBackedTasksManager();
    }
    static public TaskManager getDefaultHttpTaskManager(String url) throws IOException, InterruptedException {
        return (TaskManager) new HttpTaskManager(url);
    }
}
