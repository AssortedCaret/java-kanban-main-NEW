/**
 * Заранее прошу прощения за неработающий код, отправляю, чтобы не получить академ отпуск(вроде, если задание
 * на проверке, дают ещё время). Завтра, послезавтра пришлю уже готовый код. Этот можно даже не смотреть.
 * Спасибо за понимание)
 */

import history.InMemoryHistoryManager;
import task.Epic;
import task.SubTask;
import task.Task;
import manager.FileBackedTasksManager;
import manager.InMemoryTaskManager;

import exception.ManagerSaveException;

import java.io.File;

public class Main {
    public static void main(String[] args) throws ManagerSaveException {
        File file = new File("History.csv");
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        Task firstTask = new Task("First Task", "Make First Task", "NEW", 5);
        firstTask.setStartTime(2023, 01, 01, 01, 01);
        fileBackedTasksManager.putTask(firstTask);
        Task secondTask = new Task("Second Task", "Make Second Task", "DONE", 10);
        secondTask.setStartTime(2023, 02, 01,01,01);
        fileBackedTasksManager.putTask(secondTask);
        Epic firstEpic = new Epic("First Epic", "Make First Epic", "NEW", 20 );
        firstEpic.availabilityCheckSub();
        fileBackedTasksManager.putEpic(firstEpic);
        Epic secondEpic = new Epic("Second Epic", "Make Second Epic", "DONE", 30);
        secondEpic.availabilityCheckSub();
        fileBackedTasksManager.putEpic(secondEpic);
        SubTask firstSubTask = new SubTask("First SubTask", "Make SubTask", "DONE", 40);
        firstSubTask.setStartTime(2023, 03, 01,01,01);
        firstEpic.addSubTaskInEpic(1);
        SubTask secondSubTask = new SubTask("Second SubTask", "Make SubTask", "DONE", 50);
        secondSubTask.setStartTime(2023, 05, 01,01,01);
        secondEpic.addSubTaskInEpic(2);
        SubTask thirdSubTask = new SubTask("Third SubTask", "Make SubTask", "NEW", 60);
        thirdSubTask.setStartTime(2023, 04, 01,01,01);
        secondEpic.addSubTaskInEpic(3);
        inMemoryTaskManager.updateEpicDuration(3);
        firstEpic.availabilityCheckSub();
        secondEpic.availabilityCheckSub();
        fileBackedTasksManager.putSubTask(firstSubTask, 3);
        fileBackedTasksManager.putSubTask(secondSubTask, 3);
        fileBackedTasksManager.putSubTask(thirdSubTask, 3);

        /**
         * Проверка заполнения мап с данными
         */
//        System.out.println(inMemoryTaskManager.getTasks());
//        System.out.println(inMemoryTaskManager.getEpics());
//        System.out.println(inMemoryTaskManager.getSubTasks());
        inMemoryTaskManager.getTaskForID(1);
        inMemoryTaskManager.getTaskForID(2);
        inMemoryTaskManager.getEpicForID(3);
        inMemoryTaskManager.getSubTaskForID(6);



        inMemoryHistoryManager.add(firstTask);
        inMemoryHistoryManager.add(secondTask);
        inMemoryHistoryManager.add(firstEpic);
        inMemoryHistoryManager.add(secondEpic);
        inMemoryHistoryManager.add(firstSubTask);
        inMemoryHistoryManager.add(secondSubTask);
        inMemoryHistoryManager.add(thirdSubTask);

        inMemoryHistoryManager.getHistory();

        fileBackedTasksManager.save();
        fileBackedTasksManager.loadFromFile();


    }
}
