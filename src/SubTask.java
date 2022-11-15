import java.util.ArrayList;
import java.util.HashMap;

public class SubTask extends Task{

    private Integer epicForSubtask;

    public SubTask(String name, String description, String status) {
        super(name, description, status);
    }

    public void setEpicForSubtask(Integer epicID){
        epicForSubtask = epicID;
    }
    public Integer getEpicForSubtask(){
        return epicForSubtask;
    }
}
