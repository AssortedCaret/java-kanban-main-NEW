package tests;

import history.InMemoryHistoryManager;
import task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    InMemoryHistoryManager inMemoryHistoryManager;
    @BeforeEach
    public void madeClass(){
        inMemoryHistoryManager = new InMemoryHistoryManager();
    }
    @Test
    void add() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", "NEW", 30);
        inMemoryHistoryManager.add(task);
        final List<ArrayList> tasks = inMemoryHistoryManager.getTasks();
        assertNotNull(tasks, "Задача не добавлена");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void getHistory() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", "NEW", 30);
        inMemoryHistoryManager.add(task);
        final List<ArrayList> tasks = inMemoryHistoryManager.getTasks();
        assertNotNull(tasks, "Задача не добавлена");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void remove() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", "NEW", 30);
        inMemoryHistoryManager.add(task);
        final int taskId = task.getId();
        inMemoryHistoryManager.removeNode(taskId);
        final List<ArrayList> tasks = inMemoryHistoryManager.getTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач.");
    }
}