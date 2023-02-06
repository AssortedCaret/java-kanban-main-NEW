package manager;

import exception.ManagerSaveException;
import exception.TaskOutputException;
import history.InMemoryHistoryManager;
import task.Task;
import task.Epic;
import task.SubTask;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    static InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    static TreeSet<Task> treeSet;

    public static TreeSet<Task> getTreeSet() {
        return treeSet;
    }

    public void save() {
        writeDataInCSV();
    }

    public static void loadFromFile() throws ManagerSaveException {
        readDataInCSV();
    }

    public static void readDataInCSV() {
        String path = "History.csv";
        File file = new File(path);
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = fileReader.readLine()) != null) {
                final String fileContents = Files.readString(file.toPath());
                fromString(fileContents);
                historyToString(inMemoryHistoryManager);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TreeSet getPrioritizedTasks(Map<Integer, Task> taskMap, Map<Integer, SubTask> subTaskMap, Map<Integer,
            Epic> epicMap) {
        Comparator<Task> comparator = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (o1.getStartTime().isBefore(o2.getStartTime()))
                    return 1;
                else if (o1.getStartTime().isAfter(o2.getStartTime()))
                    return -1;
                else return 0;
            }
        };

        treeSet = new TreeSet<Task>(comparator);
        for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
            treeSet.add(entry.getValue());
        }
        for (Map.Entry<Integer, SubTask> entrySub : subTaskMap.entrySet()) {
            treeSet.add(entrySub.getValue());
        }
        return treeSet;
    }

    public void intersectionCheck(Task checkTask){
        getPrioritizedTasks(taskMap, subTaskMap, epicMap);
        for(Task task : treeSet){
            LocalDateTime startTime = task.getStartTime();
            LocalDateTime endTime = task.getEndTime();
            if((checkTask.getStartTime().isAfter(startTime) && checkTask.getEndTime().isBefore(endTime)) ||
                    (checkTask.getStartTime().isBefore(endTime) && checkTask.getEndTime().isAfter(endTime)) ||
                    (checkTask.getStartTime().isBefore(endTime) || checkTask.getEndTime().isAfter(endTime))){
                TaskOutputException exception = new TaskOutputException("Time crossing:" + checkTask + " and " + task);
            }
            else
                return;
        }
    }

    public void writeDataInCSV() {
        try (Writer writer = new FileWriter("History.csv")) {
            TreeSet<Task> arrayTask = getPrioritizedTasks(taskMap, subTaskMap, epicMap);
            for (Task nTask : arrayTask) {
                final Task task = nTask;
                writer.write(toString(task));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String toString(Task task) {
        String str = "\n" + task.getName() + "," + task.getDescription() + "," + task.getStatus();
        return str;
    }

    public static Object fromString(String value) {
        TaskType type = null;
        Epic epic = new Epic(null, null, null, 0);
        SubTask subTask = new SubTask(null, null, null, 0);
        Task task = new Task(null, null, null, 0);
        String[] taskSplit = value.split(",");
        String name = taskSplit[0];
        String descript = taskSplit[1];
        String stat = taskSplit[2];
        if (name.contains("Subtask")) {
            subTask.setName(name);
            subTask.setDescription(descript);
            subTask.setStatus(stat);
            return subTask;
        } else if (name.contains("Epic")) {
            epic.setName(name);
            epic.setDescription(descript);
            epic.setStatus(stat);
            return epic;
        } else if (name.contains("task")) {
            task.setName(name);
            task.setDescription(descript);
            task.setStatus(stat);
            return task;
        } else
            return null;
    }

    static String historyToString(InMemoryHistoryManager manager) {
        List<Task> list = manager.getHistory();
        String strHistory = "";
        for (Task task : list) {
            String strTask = toString(task);
            strHistory += "\n " + strTask;
        }

        return strHistory;
    }

    @Override
    public void putTask(Task task) {
        intersectionCheck(task);
        super.putTask(task);
        save();
    }

    @Override
    public void putEpic(Epic epic) {
        intersectionCheck(epic);
        super.putEpic(epic);
        save();
    }

    @Override
    public void putSubTask(SubTask subTask, Integer epicID) {
        intersectionCheck(subTask);
        super.putSubTask(subTask, epicID);
        save();
    }

    @Override
    //удаление по ID
    public void deleteTaskMapID(Integer number) {
        super.deleteTaskMapID(number);
        save();
    }

    @Override
    public void deleteEpicMapID(Integer number) {
        super.deleteEpicMapID(number);
        save();
    }

    @Override
    public void deleteSubTaskMapID(Integer number) {
        super.deleteSubTaskMapID(number);
        save();
    }

    @Override
    public void updateTask(Integer identifier, Task task) {
        intersectionCheck(task);
        super.updateTask(identifier, task);
        save();
    }

    @Override
    public void updateEpic(Integer identifier, Epic epic) {
        intersectionCheck(epic);
        super.updateEpic(identifier, epic);
        save();
    }

    @Override
    public void updateSubTask(Integer identifier, SubTask subTask) {
        intersectionCheck(subTask);
        super.updateSubTask(identifier, subTask);
        save();
    }

    @Override
    public void deleteTaskMap() {
        super.deleteTaskMap();
        save();
    }

    @Override
    public void deleteEpicMap() {
        super.deleteEpicMap();
        save();
    }

    @Override
    public void deleteSubTaskMap() {
        super.deleteSubTaskMap();
        save();
    }

    public Task getTaskForID(Integer number) {
        super.getTaskForID(number);
        save();
        return taskMap.get(number);
    }

    @Override
    public Epic getEpicForID(Integer number) {
        super.getTaskForID(number);
        save();
        return epicMap.get(number);
    }

    @Override
    public SubTask getSubTaskForID(Integer number) {
        super.getTaskForID(number);
        save();
        return subTaskMap.get(number);
    }
}
