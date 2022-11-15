public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Task firstTask = new Task("First Task", "Make First Task", "NEW");
        manager.putTask(firstTask);
        Task secondTask = new Task("Second Task", "Make Second Task", "DONE");
        manager.putTask(secondTask);
        Epic firstEpic = new Epic("First Epic", "Make First Epic", "NEW" );
        manager.putEpic(firstEpic);
        SubTask firstSubTask = new SubTask("First SubTask", "Make SubTask", "IN_PROGRESS");
        firstEpic.addSubTaskInEpic(1);
        Epic secondEpic = new Epic("Second Epic", "Make Second Epic", "NEW");
        manager.putEpic(secondEpic);
        SubTask secondSubTask = new SubTask("Second SubTask", "Make SubTask", "DONE");
        secondEpic.addSubTaskInEpic(2);
        SubTask thirdSubTask = new SubTask("Third SubTask", "Make SubTask", "NEW");
        secondEpic.addSubTaskInEpic(3);
        manager.putSubTask(firstSubTask, 3);
        manager.putSubTask(secondSubTask, 4);
        manager.putSubTask(thirdSubTask, 4);

//      меняем значения
        Task newTask = new Task("new Task", "Make new Task", "IN_PROGRESS");
        manager.updateTask(1, newTask);
        SubTask newFirstSubTask = new SubTask("New First SubTask", "Make SubTask", "DONE");
        manager.updateSubTask(5, newFirstSubTask);
        manager.putSubTask(newFirstSubTask, 3);
//      удаление
        manager.deleteEpicMapID(3);
        manager.deleteTaskMapID(1);

        System.out.println(manager.getTaskMap());
        System.out.println(manager.getEpicMap());
        System.out.println(manager.getSubTaskMap());
    }
}
