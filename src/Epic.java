import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    public ArrayList <Integer> subTaskInEpic = new ArrayList<>();

    public Epic(String name, String description, String status) {
        super(name, description, status);
    }

    // метод для связи эпика и подзадачи(при создании подзадачи вызывать этот метод)
    public void addSubTaskInEpic(Integer subTaskID){
        subTaskInEpic.add(subTaskID);
    }

    //получить значения подзадач в эпике
    public List<Integer> getSubTaskFromEpic(){
        return subTaskInEpic;
    }
}
