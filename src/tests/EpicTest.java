package tests;

import task.Epic;
import task.SubTask;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EpicTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    Epic epic = new Epic("Test Epic", "Epic description", "NEW", 30);

    @BeforeEach
    public void afterEach(){
        inMemoryTaskManager.deleteSubTaskMap();
        inMemoryTaskManager.deleteEpicMap();
        inMemoryTaskManager.putEpic(epic);
    }
    @Test
    public void shouldEpicStatusFromAnEmptyList(){
        if(epic.getSubTaskFromEpic().equals(null)){
            Assertions.assertEquals("NEW", epic.getStatus(), "Epic have not subTask and should be NEW");
        }
    }

    @Test
    public void shouldEpicStatusBeNew(){
        SubTask subTaskFirst = new SubTask("SubTaskFirst", "DescriptionSubFirst", "NEW", 30);
        SubTask subTaskSecond = new SubTask("SubTaskSec", "DescriptionSubSec", "NEW", 30);
        inMemoryTaskManager.putSubTask(subTaskFirst, 1);
        inMemoryTaskManager.putSubTask(subTaskSecond, 1);
        epic.addSubTaskInEpic(1);
        epic.addSubTaskInEpic(2);
        Assertions.assertEquals("NEW", epic.getStatus());
    }

    @Test
    public void shouldEpicStatusBeDone(){
        SubTask subTaskFirst = new SubTask("SubTaskFirst", "DescriptionSubFirst", "DONE", 30);
        SubTask subTaskSecond = new SubTask("SubTaskSec", "DescriptionSubSec", "DONE", 30);
        inMemoryTaskManager.putSubTask(subTaskFirst, 1);
        inMemoryTaskManager.putSubTask(subTaskSecond, 1);
        epic.addSubTaskInEpic(1);
        epic.addSubTaskInEpic(2);
        Assertions.assertEquals("DONE", epic.getStatus());
    }

    @Test
    public void shouldEpicStatusBeNewIfHaveDone(){
        SubTask subTaskFirst = new SubTask("SubTaskFirst", "DescriptionSubFirst", "DONE", 30);
        SubTask subTaskSecond = new SubTask("SubTaskSec", "DescriptionSubSec", "NEW", 30);
        inMemoryTaskManager.putSubTask(subTaskFirst, 1);
        inMemoryTaskManager.putSubTask(subTaskSecond, 1);
        epic.addSubTaskInEpic(1);
        epic.addSubTaskInEpic(2);
        Assertions.assertEquals("NEW", epic.getStatus());
    }
}