package TaskManager;

import Task.*;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    ArrayList getTaskMap();
    ArrayList getEpicMap();
    ArrayList getSubTaskMap();
    void deleteTaskMap();
    void deleteEpicMap();
    void deleteSubTaskMap();
    Task getTaskForID(Integer number);
    Epic getEpicForID(Integer number);
    SubTask getSubTaskForID(Integer number);
    void putTask(Task task);
    void putEpic(Epic epic);
    void putSubTask(SubTask subTask, Integer epicID);
    void updateTask(Integer identifier, Task task);
    void updateEpic(Integer identifier, Epic epic);
    void updateSubTask(Integer identifier, SubTask subTask);
    void deleteTaskMapID(Integer number);
    void deleteEpicMapID(Integer number);
    void deleteSubTaskMapID(Integer number);
    List<SubTask> getSubTaskEpic(Epic epic);
    Integer getID();
    void updateEpicStatus(Integer epicId);
}
