package tests;

import manager.FileBackedTasksManager;
import util_manager.Managers;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    @Override
    public FileBackedTasksManager createManager(){
        return (FileBackedTasksManager) Managers.getDefaultFile();
    }

}