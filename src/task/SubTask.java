package task;

public class SubTask extends Task{

    private Integer epicId;

    public SubTask(String name, String description, String status, int time) {
        super(name, description, status, time);
    }

    public void setEpicId(Integer epicID){
        epicId = epicID;
    }
    public Integer getEpicId(){
        return epicId;
    }
}
