package mk.ukim.finki.tasks.domain.models;

import lombok.Data;
import mk.ukim.finki.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.tasks.domain.valueobjects.Duration;
import mk.ukim.finki.tasks.domain.valueobjects.Progress;
import mk.ukim.finki.tasks.domain.valueobjects.Status;
import mk.ukim.finki.tasks.domain.valueobjects.Time;
import mk.ukim.finki.users.domain.model.User;
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
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Nullable
    private Time startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Nullable
    private Time endTime;

    @Nullable
    private Progress progress;


    public String getUserName(){
        if(this.user==null){
            return "No users for this task";
        }
        return this.user.getName() + " " + this.user.getSurname();
    }

    public Task() {
    }

    public Task( String title, String description, Status status, List<Task> dependsOn, User user, Time startTime, Time endTime,Progress progress) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dependsOn = dependsOn;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.progress=progress;
    }

}
