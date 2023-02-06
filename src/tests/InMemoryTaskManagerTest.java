package tests;

import manager.InMemoryTaskManager;
import util_manager.Managers;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @Override
    public InMemoryTaskManager createManager(){
        return (InMemoryTaskManager) Managers.getDefault();
    }
}