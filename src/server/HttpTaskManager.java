package server;
/**
 * Не разобрался как именно работает этот класс, можешь подсказать, пожалуйста? С данным классом очень СИЛЬНО помогли.
 * В пачке узнавал, но так и не догнал
 */

import com.google.gson.Gson;
import manager.FileBackedTasksManager;
import com.google.gson.*;
import task.Epic;
import task.SubTask;
import task.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HttpTaskManager extends FileBackedTasksManager {
    KVTaskClient kVTaskClient;
    Gson json = new Gson();

    public HttpTaskManager(String URL) throws IOException, InterruptedException {
        this.kVTaskClient = new KVTaskClient(URL);
        loadFile();
    }

    @Override
    public void save() {
        kVTaskClient.put("task", json.toJson(super.getTasks()));
        kVTaskClient.put("epic", json.toJson(super.getEpics()));
        kVTaskClient.put("subTask", json.toJson(super.getSubTasks()));
        kVTaskClient.put("history", json.toJson(super.getHistory()));
    }


    public void loadFile() {
        ArrayList<String> jArray = new ArrayList<>(Collections.singleton(kVTaskClient.load("task")));
        if (jArray == null) {
            return;
        }
        for (String jsonTask : jArray) {
            Task loadedTask = json.fromJson(jsonTask, Task.class);
            super.putTask(loadedTask);
        }
        jArray = new ArrayList<>(Collections.singleton(kVTaskClient.load("epic")));
        if (jArray == null) {
            return;
        }
        for (String jsonEpic : jArray) {
            Epic loadedEpic = json.fromJson(jsonEpic, Epic.class);
            super.putEpic(loadedEpic);
        }
        jArray = new ArrayList<>(Collections.singleton(kVTaskClient.load("subTask")));
        if (jArray == null) {
            return;
        }
        for (String jsonSubTask : jArray) {
            SubTask loadedTask = json.fromJson(jsonSubTask, SubTask.class);
            super.putSubTask(loadedTask, 1);
        }
        jArray = new ArrayList<>(Collections.singleton(kVTaskClient.load("history")));
        if (jArray == null) {
            return;
        }
        for (String jsonTaskId : jArray) {
            int loadedId = Integer.parseInt(jsonTaskId);
            if (epicMap.containsKey(loadedId)) {
                getEpicForID(loadedId);
            } else if (taskMap.containsKey(loadedId)) {
                getTaskForID(loadedId);
            } else if (subTaskMap.containsKey(loadedId)) {
                getSubTaskForID(loadedId);
            }
        }
    }
}
