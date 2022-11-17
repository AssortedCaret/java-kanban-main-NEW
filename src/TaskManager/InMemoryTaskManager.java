package TaskManager;

import Task.*;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private final HashMap<Integer, Task> taskMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();
    private final HashMap<Integer, SubTask> subTaskMap = new HashMap<>();
    private Integer ID = 0;

    // получение списка всех задач
    @Override
    public ArrayList getTaskMap(){
        ArrayList<Task> taskList = new ArrayList<>();
        for(Task task : taskMap.values())
            taskList.add(task);
        return taskList;
    }

    @Override
    public ArrayList getEpicMap(){
        ArrayList<Epic> epicList = new ArrayList<>();
        for(Epic epic : epicMap.values())
            epicList.add(epic);
        return epicList;
    }

    @Override
    public ArrayList getSubTaskMap(){
        ArrayList<SubTask> epicList = new ArrayList<>();
        for(SubTask subTask : subTaskMap.values())
            epicList.add(subTask);
        return epicList;
    }

    //удаление задач, подзадач, эпиков
    @Override
    public void deleteTaskMap(){
        taskMap.clear();
    }

    @Override
    public void deleteEpicMap(){
        epicMap.clear();
    }

    @Override
    public void deleteSubTaskMap(){
        subTaskMap.clear();
        for(Integer number : epicMap.keySet())
            updateEpicStatus(number);
    }

    // получение объектов по идентификатору
    public Task getTaskForID(Integer number){
        Task newTask = taskMap.get(number);
        return newTask;
    }

    @Override
    public Epic getEpicForID(Integer number){
        Epic newEpic = epicMap.get(number);
        return newEpic;
    }

    @Override
    public SubTask getSubTaskForID(Integer number){
        SubTask newSubTask = subTaskMap.get(number);
        return newSubTask;
    }

    //добавление объектов, получение ID
    @Override
    public void putTask(Task task){
        taskMap.put(getID(), task);
    }

    @Override
    public void putEpic(Epic epic){
        epicMap.put(getID(), epic);
    }

    @Override
    public void putSubTask(SubTask subTask, Integer epicID){
        subTask.setEpicForSubtask(epicID);
        subTaskMap.put(getID(), subTask);
        updateEpicStatus(subTask.getEpicForSubtask());
    }

    /* обновление задач, подзадач, эпиков (2.5: Обновление. Новая версия объекта с верным идентификатором передаётся
    в виде параметра)
     */
    @Override
    public void updateTask(Integer identifier, Task task) {
        for (Integer number : taskMap.keySet()) {
            if (number.equals(identifier)) {
                taskMap.put(number, task);
            }
        }
    }

    @Override
    public void updateEpic(Integer identifier, Epic epic) {
        for (Integer number : epicMap.keySet()) {
            if (number.equals(identifier)) {
                epicMap.put(number, epic);
            }
        }
    }

    @Override
    public void updateSubTask(Integer identifier, SubTask subTask) {
        SubTask oldSubTask;
        Integer epicIdent;
        if(subTaskMap.get(identifier) != null) {
            oldSubTask = subTaskMap.get(identifier);
            epicIdent = oldSubTask.getEpicForSubtask();
            subTask.setEpicForSubtask(epicIdent);
            subTaskMap.put(identifier, subTask);
        }else
            System.out.println("Подзадача с таким идентификатором не найдена");
        updateEpicStatus(subTask.getEpicForSubtask());
    }

    @Override
    //удаление по ID
    public void deleteTaskMapID(Integer number){
        Iterator iteratorTask = taskMap.keySet().iterator();
        if(iteratorTask.hasNext()) {
            if (iteratorTask.next().equals(number)) {
                iteratorTask.remove();
            }
        }
    }

    @Override
    public void deleteEpicMapID(Integer number){
        Iterator iteratorEpic = epicMap.keySet().iterator();
        if(iteratorEpic.hasNext()) {
            if (iteratorEpic.next().equals(number)) {
                iteratorEpic.remove();
            }
        }
    }

    @Override
    public void deleteSubTaskMapID(Integer number){
        Iterator iteratorSubTask = subTaskMap.keySet().iterator();
        if(iteratorSubTask.hasNext()) {
            if (iteratorSubTask.next().equals(number)) {
                iteratorSubTask.remove();
            }
        }
        updateEpicStatus(subTaskMap.get(number).getEpicForSubtask());
    }

    // получение списка подзадач определенного эпика
    @Override
    public List<SubTask> getSubTaskEpic(Epic epic){
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for ( Integer subTaskId : epic.getSubTaskFromEpic() ) {
            subTasks.add(subTaskMap.get(subTaskId));
        }
        return subTasks;
    }

    @Override
    public Integer getID(){
        ID += 1;
        return ID;
    }

    @Override
    public void updateEpicStatus(Integer epicId){
        boolean isAllSubtasksNEW = true;
        boolean ifAllSubtasksDONE = true;
        Epic epic = epicMap.get(epicId);
        if (epic.subTaskInEpic == null)
            epic.setStatus("NEW");
        for (Map.Entry<Integer, SubTask> subTaskM : subTaskMap.entrySet()) {
            Integer numb = subTaskM.getKey();
            SubTask subTask = subTaskM.getValue();
            if (!subTask.getStatus().equals(String.valueOf(StatusEpic.NEW)))
                isAllSubtasksNEW = false;
            else if (subTask.getStatus().equals(String.valueOf(StatusEpic.DONE)))
                ifAllSubtasksDONE = false;
        }
        if(isAllSubtasksNEW == true)
            epic.setStatus(String.valueOf(StatusEpic.NEW));
        else if(ifAllSubtasksDONE == true && isAllSubtasksNEW == false)
            epic.setStatus(String.valueOf(StatusEpic.DONE));
        else
            epic.setStatus(String.valueOf(StatusEpic.IN_PROGRESS));
    }

}
















