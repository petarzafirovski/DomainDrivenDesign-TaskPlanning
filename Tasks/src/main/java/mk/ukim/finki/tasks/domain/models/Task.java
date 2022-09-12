package mk.ukim.finki.tasks.domain.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NonNull;
import mk.ukim.finki.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.tasks.domain.valueobjects.Duration;

import mk.ukim.finki.tasks.domain.valueobjects.Progress;
import mk.ukim.finki.tasks.domain.valueobjects.Status;
import mk.ukim.finki.tasks.domain.valueobjects.Time;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tasks")
public class Task extends AbstractEntity<TaskId> {

    private String title;
    private String description;

    private Duration duration;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Task> dependsOn;

    @ManyToOne
    private TaskUser user;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @AttributeOverride(name = "time", column = @Column(name = "start_time", nullable = false))
    private Time startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @AttributeOverride(name = "time", column = @Column(name = "end_time", nullable = false))
    private Time endTime;

    @Nullable
    private Progress progress;


//    public String getUserName(){
//        if(this.user==null){
//            return "No users for this task";
//        }
//        return this.user.getName() + " " + this.user.getSurname();
//    }


    public Task() {
        super(DomainObjectId.randomId(TaskId.class));
    }

    public Task(String title, String description, Status status, List<Task> dependsOn, TaskUser user, Time startTime, Time endTime, Progress progress) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dependsOn = dependsOn;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.progress = progress;
    }

    //TODO: Implement data validation, adding a task, progress calculation, status calculation

    public void setStatusFromProgress(Progress progress) {
        if (progress == null || progress.getProgress() == (0.0)) {
            this.status = Status.todo;
        }
        if (progress.getProgress() == 1) {
            this.status = Status.finished;
            return;
        }
        if (progress.getProgress() >= 0.8) {
            this.status = Status.forReview;
            return;
        }
        else if (progress.getProgress() > 0.0) {
            this.status = Status.inProgress;
        }
    }

    public void setStatus(String status){
        this.status=Status.valueOf(status);
    }

    //add task dependants
    public Task addDependency(@NotNull Task task) {
        if (!this.dependsOn.contains(task)) {
            this.dependsOn.add(task);
        }
        return task;
    }

    public void removeDependency(@NotNull Task task) {
        this.dependsOn.remove(task);
    }

    //add duration
    public void setDuration(@NonNull Duration duration) {
        if (duration.getDuration() < 0L) {
            throw new IllegalArgumentException("duration cannot be less than 0");
        } else {
            this.duration = duration;
        }
    }

    //start time end time validacija
    public void setStartTime(@NonNull Time startTime) {
        if (endTime != null) {
            if (startTime.getTime().isAfter(endTime.getTime()) || startTime.getTime().equals(endTime.getTime())) {
                throw new IllegalArgumentException("invalid start time");
            }

        }
        this.startTime = startTime;
    }

    public void setEndTime(@NonNull Time endTime) {
        if (startTime != null) {
            if (endTime.getTime().isBefore(startTime.getTime()) || endTime.getTime().equals(startTime.getTime())) {
                throw new IllegalArgumentException("invalid end time");
            }
        }
        this.endTime = endTime;
    }

    //est time in hours
    public Long findEstTimeInDays(Time startTime, Time endTime) {
        if (endTime == null){
            throw new IllegalArgumentException("duration cannot be calculated");
        }
        if (startTime != null)
            return Duration.between(this.startTime.getTime(), this.endTime.getTime()).toDays();
        return Duration.between(LocalDateTime.now(), this.endTime.getTime()).toDays();
    }



    public static Task build(String title, String description, List<Task> dependsOn, TaskUser user, Time startTime,Time endTime, @Nullable Progress progress,String status) {
        Task task = new Task();
        task.setTitle(title);
        if(status.equals("undefined") || status == null ){
            task.setStatusFromProgress(progress);
        }else{
            task.setStatus(status);
        }
        task.setDescription(description);
        task.setDependsOn(dependsOn);
        task.setStartTime(startTime );
        task.setEndTime(endTime);
        task.setProgress(progress);
        if(startTime!=null && endTime == null){
            Duration duration = new Duration(1L);
            task.setDuration(duration);
        }

        if(startTime!=null && endTime!=null){
            Long dur = task.findEstTimeInDays(startTime,endTime);
            Duration duration = new Duration(dur);
            task.setDuration(duration);
        }
        task.setUser(user);

        return task;
    }
}
