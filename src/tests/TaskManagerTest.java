package tests;

import task.Task;
import task.Epic;
import task.SubTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import manager.TaskManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    public abstract T createManager();
    T manager;

    @BeforeEach
    public void before() {
        manager = createManager();
    }

    @AfterEach
    public void after(){
        manager.deleteTaskMap();
        manager.deleteEpicMap();
        manager.deleteSubTaskMap();
    }

    @Test
    void addTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", "NEW", 30);
        manager.putTask(task);
        final int taskId = task.getId();

        final Task savedTask = manager.getTaskForID(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addEpic() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", "NEW", 30);
        manager.putEpic(epic);
        final int taskId = epic.getId();

        final Task savedTask = manager.getEpicForID(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(epic, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getEpics();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(epic, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addSubTask() {
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                "NEW", 30);
        manager.putSubTask(subTask, null);
        final int subTaskId = subTask.getId();

        final SubTask savedSubTask = manager.getSubTaskForID(subTaskId);

        assertNotNull(savedSubTask, "Задача не найдена.");
        assertEquals(subTask, savedSubTask, "Задачи не совпадают.");

        final List<SubTask> subTasks = manager.getSubTasks();

        assertNotNull(subTasks, "Задачи на возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void shouldDeleteTask(){
        Task task = new Task("Test addNewTask", "Test addNewTask description", "NEW", 30);
        manager.putTask(task);
        manager.deleteTaskMap();
        final List<Task> tasks = manager.getTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void shouldDeleteEpic(){
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", "NEW", 30);
        manager.putEpic(epic);
        manager.deleteEpicMap();
        final List<Epic> tasks = manager.getEpics();
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void shouldDeleteSubTask(){
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                "NEW", 30);
        manager.putSubTask(subTask, null);
        manager.deleteSubTaskMap();
        final List<Task> tasks = manager.getTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void shouldDeleteTaskId(){
        Task task = new Task("Test addNewTask", "Test addNewTask description", "NEW", 30);
        manager.putTask(task);
        manager.deleteTaskMapID(1);
        final List<Task> tasks = manager.getTasks();
        assertEquals(1, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void shouldDeleteEpicId(){
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", "NEW", 30);
        manager.putEpic(epic);
        manager.deleteEpicMapID(1);
        final List<Epic> tasks = manager.getEpics();
        assertEquals(1, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void shouldUpdateTask(){
        Task task = new Task("Test addNewTask", "Test addNewTask description", "NEW", 30);
        manager.putTask(task);
        Task upTask = new Task("Test addNewUpTask", "Test addNewUpTask description", "NEW",
                30);
        manager.updateTask(1, upTask);
        final List<Task> newTasks = manager.getTasks();
        assertEquals(upTask, newTasks.get(0));
    }
}