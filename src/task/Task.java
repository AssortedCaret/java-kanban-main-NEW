package task;

import manager.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;


public class Task{
    private String name;
    private String description;
    private String status;
    private Integer id;
    private Duration duration;
    private LocalDateTime startTime;
    private int minutes;
    private LocalDateTime endTime;

    public Task(String name, String description, String status, int minutes){
        this.id = InMemoryTaskManager.getID();
        this.name = name;
        this.description = description;
        this.status = status;
        duration = getDuration(minutes);
    }

    public void setStartTime(int year, int month, int day, int hour, int minutes){
        this.startTime = LocalDateTime.of(year, month, day, hour, minutes);
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public Duration getDuration(int time){
      this.duration = Duration.ofMinutes(time);
      return duration;
    }

    public LocalDateTime getEndTime(){
        endTime = startTime.plus(duration);
        return endTime;
    }

    public Integer getId() {
        return id;
    }
    @Override
    public String toString() {
        return "Task.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", startTime='" + startTime + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) 
                && Objects.equals(status, task.status) && Objects.equals(minutes, task.minutes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
