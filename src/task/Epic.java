package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    public ArrayList <Integer> subTaskIds = new ArrayList<>();
    LocalDateTime endTime;
    public Epic(String name, String description, String status, int time) {
        super(name, description, status,  time);
    }

    // метод для связи эпика и подзадачи(при создании подзадачи вызывать этот метод)
    public void addSubTaskInEpic(Integer subTaskID){
        subTaskIds.add(subTaskID);
    }

    //получить значения подзадач в эпике
    public List<Integer> getSubTaskFromEpic(){
        return subTaskIds;
    }

    public LocalDateTime getEndTime(LocalDateTime time){
        this.endTime = time;
        return endTime;
    }

    public void availabilityCheckSub(){
        if(subTaskIds.size() == 0)
            setStartTime(0,1,1,0,0);
        else return;

    }
    public Duration getDuration(){
        Duration newTime = Duration.ofDays(Duration.between(getStartTime(), endTime).toMinutes());
        return newTime;
    }
}
