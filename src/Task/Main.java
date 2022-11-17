package Task;

import History.HistoryManager;
import TaskManager.InMemoryTaskManager;
import UtilManager.Managers;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task firstTask = new Task("First Task.Task", "Make First Task.Task", "NEW");
        inMemoryTaskManager.putTask(firstTask);
        Task secondTask = new Task("Second Task.Task", "Make Second Task.Task", "DONE");
        inMemoryTaskManager.putTask(secondTask);
        Epic firstEpic = new Epic("First Task.Epic", "Make First Task.Epic", "NEW" );
        inMemoryTaskManager.putEpic(firstEpic);
        SubTask firstSubTask = new SubTask("First Task.SubTask", "Make Task.SubTask", "IN_PROGRESS");
        firstEpic.addSubTaskInEpic(1);
        Epic secondEpic = new Epic("Second Task.Epic", "Make Second Task.Epic", "NEW");
        inMemoryTaskManager.putEpic(secondEpic);
        SubTask secondSubTask = new SubTask("Second Task.SubTask", "Make Task.SubTask", "DONE");
        secondEpic.addSubTaskInEpic(2);
        SubTask thirdSubTask = new SubTask("Third Task.SubTask", "Make Task.SubTask", "NEW");
        secondEpic.addSubTaskInEpic(3);
        inMemoryTaskManager.putSubTask(firstSubTask, 3);
        inMemoryTaskManager.putSubTask(secondSubTask, 4);
        inMemoryTaskManager.putSubTask(thirdSubTask, 4);

//      меняем значения
        Task newTask = new Task("new Task.Task", "Make new Task.Task", "IN_PROGRESS");
        inMemoryTaskManager.updateTask(1, newTask);
        SubTask newFirstSubTask = new SubTask("New First Task.SubTask", "Make Task.SubTask", "DONE");
        inMemoryTaskManager.updateSubTask(5, newFirstSubTask);
        inMemoryTaskManager.putSubTask(newFirstSubTask, 3);
//      удаление
        inMemoryTaskManager.deleteEpicMapID(3);
        inMemoryTaskManager.deleteTaskMapID(1);

        System.out.println(inMemoryTaskManager.getTaskMap());
        System.out.println(inMemoryTaskManager.getEpicMap());
        System.out.println(inMemoryTaskManager.getSubTaskMap());

        //проверка истории
        /*Насколько понял, теперь мы вызываем методы объектов через класс Manager, и нам теперь не нужно хранить
        * истрию в классе InMemoryTaskManager - оттуда убираем всё, что с историей связано. Для истории используется
        * только класс InMemoryHistoryManager, и работать с ним, как сделал ниже*/

        HistoryManager managers = Managers.getDefaultHistory();
        managers.add(newFirstSubTask);
        managers.add(secondTask);
        System.out.println(managers.getHistory());


    }
}
