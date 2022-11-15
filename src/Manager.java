import java.util.*;

public class Manager {

    private final HashMap<Integer, Task> taskMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();
    private final HashMap<Integer, SubTask> subTaskMap = new HashMap<>();
    private Integer ID = 0;

    // получение списка всех задач
    public ArrayList getTaskMap(){
        ArrayList<Task> taskList = new ArrayList<>();
        for(Task task : taskMap.values())
            taskList.add(task);
        return taskList;
    }

    public ArrayList getEpicMap(){
        ArrayList<Epic> epicList = new ArrayList<>();
        for(Epic epic : epicMap.values())
            epicList.add(epic);
        return epicList;
    }

    public ArrayList getSubTaskMap(){
        ArrayList<SubTask> epicList = new ArrayList<>();
        for(SubTask subTask : subTaskMap.values())
            epicList.add(subTask);
        return epicList;
    }

    //удаление задач, подзадач, эпиков
    public void deleteTaskMap(){
        taskMap.clear();
    }

    public void deleteEpicMap(){
        epicMap.clear();
    }

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

    public Epic getEpicForID(Integer number){
        Epic newEpic = epicMap.get(number);
        //newEpic.getStatusOfTask();
        return newEpic;
    }

    public SubTask getSubTaskForID(Integer number){
        SubTask newSubTask = subTaskMap.get(number);
        return newSubTask;
    }

    //добавление объектов, получение ID
    public void putTask(Task task){
        taskMap.put(getID(), task);
    }

    public void putEpic(Epic epic){
        epicMap.put(getID(), epic);
    }

    public void putSubTask(SubTask subTask, Integer epicID){
        subTask.setEpicForSubtask(epicID);
        subTaskMap.put(getID(), subTask);
        updateEpicStatus(subTask.getEpicForSubtask());
    }

    /* обновление задач, подзадач, эпиков (2.5: Обновление. Новая версия объекта с верным идентификатором передаётся
    в виде параметра)
     */
    public void updateTask(Integer identifier, Task task) {
        for (Integer number : taskMap.keySet()) {
            if (number.equals(identifier)) {
                taskMap.put(number, task);
            }
        }
    }

    public void updateEpic(Integer identifier, Epic epic) {
        for (Integer number : epicMap.keySet()) {
            if (number.equals(identifier)) {
                epicMap.put(number, epic);
            }
        }
    }

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

    //удаление по ID
    public void deleteTaskMapID(Integer number){
        Iterator iteratorTask = taskMap.keySet().iterator();
        if(iteratorTask.hasNext()) {
            if (iteratorTask.next().equals(number)) {
                iteratorTask.remove();
            }
        }
    }

    public void deleteEpicMapID(Integer number){
        Iterator iteratorEpic = epicMap.keySet().iterator();
        if(iteratorEpic.hasNext()) {
            if (iteratorEpic.next().equals(number)) {
                iteratorEpic.remove();
            }
        }
    }

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
    public List<SubTask> getSubTaskEpic(Epic epic){
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for ( Integer subTaskId : epic.getSubTaskFromEpic() ) {
            subTasks.add(subTaskMap.get(subTaskId));
        }
        return subTasks;
    }

    public Integer getID(){
        ID += 1;
        return ID;
    }

    private void updateEpicStatus(Integer epicId){
        boolean isAllSubtasksNEW = true;
        boolean ifAllSubtasksDONE = true;
        Epic epic = epicMap.get(epicId);
        if (epic.subTaskInEpic == null)
            epic.setStatus("NEW");
        for (Map.Entry<Integer, SubTask> subTaskM : subTaskMap.entrySet()) {
            Integer numb = subTaskM.getKey();
            SubTask subTask = subTaskM.getValue();
            if (!subTask.getStatus().equals("NEW"))
                isAllSubtasksNEW = false;
            else if (subTask.getStatus().equals("DONE"))
                ifAllSubtasksDONE = false;
        }
        if(isAllSubtasksNEW == true)
            epic.setStatus("NEW");
        else if(ifAllSubtasksDONE == true && isAllSubtasksNEW == false)
            epic.setStatus("DONE");
        else
            epic.setStatus("IN_PROGRESS");
    }


}
















