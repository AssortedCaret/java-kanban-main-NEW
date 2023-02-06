package manager;

import exception.TaskOutputException;
import history.HistoryManager;
import util_manager.*;
import task.*;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager extends TaskOutputException implements TaskManager {

    protected static HistoryManager customMap;
    protected final HashMap<Integer, Task> taskMap = new HashMap<>();

    protected HashMap<Integer, Epic> epicMap = new HashMap<>();
    protected HashMap<Integer, SubTask> subTaskMap = new HashMap<>();
    private static Integer ID = 0;

    private final HistoryManager historyManager = Managers.getDefaultHistory();
    // получение списка всех задач
    @Override
    public ArrayList getTasks(){
        ArrayList<Task> taskList = new ArrayList<>();
        for(Task task : taskMap.values()){
            taskList.add(task);
        }
        return taskList;
    }

    @Override
    public ArrayList getEpics(){

        ArrayList<Epic> epicList = new ArrayList<>();
        for(Epic epic : epicMap.values()) {
            epicList.add(epic);
        }
        return epicList;
    }

    @Override
    public ArrayList getSubTasks(){

        ArrayList<SubTask> epicList = new ArrayList<>();
        for(SubTask subTask : subTaskMap.values()) {
            epicList.add(subTask);
        }
        return epicList;
    }

    //удаление задач, подзадач, эпиков
    @Override
    public void deleteTaskMap(){
        taskMap.clear();
        for(Integer number : taskMap.keySet())
            historyManager.remove(number);
    }

    @Override
    public void deleteEpicMap(){
        epicMap.clear();
        for(Integer number : epicMap.keySet())
            historyManager.remove(number);
    }

    @Override
    public void deleteSubTaskMap(){
        subTaskMap.clear();
        for(Integer number : epicMap.keySet())
            updateEpicStatus(number);

        for(Integer number : subTaskMap.keySet())
            historyManager.remove(number);
    }

    // получение объектов по идентификатору
    public Task getTaskForID(Integer number){
        Task newTask = taskMap.get(number);
        historyManager.add(newTask);
        return newTask;
    }

    @Override
    public Epic getEpicForID(Integer number){
        Epic newEpic = epicMap.get(number);
        historyManager.add(newEpic);
        return newEpic;
    }

    @Override
    public SubTask getSubTaskForID(Integer number){
        SubTask newSubTask = subTaskMap.get(number);
        historyManager.add(newSubTask);
        return newSubTask;
    }

    //добавление объектов, получение ID
    @Override
    public void putTask(Task task){
        taskMap.put(task.getId(), task);
    }

    @Override
    public void putEpic(Epic epic){
        epicMap.put(epic.getId(), epic);
    }

    @Override
    public void putSubTask(SubTask subTask, Integer epicID){
        subTask.setEpicId(epicID);
        subTaskMap.put(subTask.getId(), subTask);
        updateEpicStatus(subTask.getEpicId());
    }

    /* Обновление задач, подзадач, эпиков (2.5: Обновление. Новая версия объекта с верным идентификатором передаётся
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
            epicIdent = oldSubTask.getEpicId();
            subTask.setEpicId(epicIdent);
            subTaskMap.put(identifier, subTask);
        }else
            System.out.println("Подзадача с таким идентификатором не найдена");
        updateEpicStatus(subTask.getEpicId());
    }

    @Override
    //удаление по ID
    public void deleteTaskMapID(Integer number){
        Iterator iteratorTask = taskMap.keySet().iterator();
        if(iteratorTask.hasNext()) {
            if (iteratorTask.next().equals(number)) {
                iteratorTask.remove();
                historyManager.remove(number);// удаление задачи из истории
            }
        }
    }

    @Override
    public void deleteEpicMapID(Integer number){
        Iterator iteratorEpic = epicMap.keySet().iterator();
        if(iteratorEpic.hasNext()) {
            if (iteratorEpic.next().equals(number)) {
                iteratorEpic.remove();
                historyManager.remove(number); // удаление задачи из истории
            }
        }
    }

    @Override
    public void deleteSubTaskMapID(Integer number){
        Iterator iteratorSubTask = subTaskMap.keySet().iterator();
        if(iteratorSubTask.hasNext()) {
            if (iteratorSubTask.next().equals(number)) {
                iteratorSubTask.remove();
                historyManager.remove(number);// удаление задачи из истории
            }
        }
        if(subTaskMap.get(number).getEpicId() != null)
            updateEpicStatus(subTaskMap.get(number).getEpicId());
        else
            return;
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

    public static Integer getID(){
        ID += 1;
        return ID;
    }

    public void updateEpicDuration(Integer epicId){
        if(epicId != 0){
            Epic epic = epicMap.get(epicId);
            for (Map.Entry<Integer, SubTask> subTaskM : subTaskMap.entrySet()) {
                Integer numb = subTaskM.getKey();
                SubTask subTask = subTaskM.getValue();
                LocalDateTime lastTime = LocalDateTime.MIN;
                LocalDateTime firstTime = LocalDateTime.MAX;
                if(firstTime.isAfter(subTask.getStartTime()))
                    firstTime = subTask.getStartTime();
                epic.setStartTime(firstTime.getYear(), firstTime.getDayOfMonth(), firstTime.getDayOfMonth(),
                        firstTime.getHour(), firstTime.getMinute());
                if(lastTime.isBefore(subTask.getEndTime())){
                    lastTime = subTask.getEndTime();
                    epic.getDuration();
                }
            }
        }
    }
    @Override
    public void updateEpicStatus(Integer epicId){
        if(epicId != null) {
            boolean isAllSubtasksNEW = true;
            boolean ifAllSubtasksDONE = true;
            if (epicMap.get(epicId) != null) {
                Epic epic = epicMap.get(epicId);
                if (epic.subTaskIds == null)
                    epic.setStatus("NEW");
                for (Map.Entry<Integer, SubTask> subTaskM : subTaskMap.entrySet()) {
                    Integer numb = subTaskM.getKey();
                    SubTask subTask = subTaskM.getValue();
                    if (!subTask.getStatus().equals(String.valueOf(Status.NEW)))
                        isAllSubtasksNEW = false;
                    else if (!subTask.getStatus().equals(String.valueOf(Status.DONE)))
                        ifAllSubtasksDONE = false;
                    /**
                     * мб баг
                     */
//                    LocalDateTime time = null;
//                    if(time.isBefore(subTask.getDuration())) {
//                        time = subTask.getDuration();
//                        epic.getEndTime(time);
//                    }
                }
                if (isAllSubtasksNEW == true)
                    epic.setStatus(String.valueOf(Status.NEW));
                else if (ifAllSubtasksDONE == true && isAllSubtasksNEW == false)
                    epic.setStatus(String.valueOf(Status.DONE));
                else
                    epic.setStatus(String.valueOf(Status.IN_PROGRESS));
            } else
                System.out.println("");
        }
    }

    @Override
    public List getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public HashMap<Integer, Task> getTaskMap() {
        return taskMap;
    }

    @Override
    public HashMap<Integer, Epic> getEpicMap() {
        return epicMap;
    }

    @Override
    public HashMap<Integer, SubTask> getSubTaskMap() {
        return subTaskMap;
    }
}
















