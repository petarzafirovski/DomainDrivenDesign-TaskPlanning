package mk.ukim.finki.tasks.service.form;

import lombok.Data;
import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.domain.models.TaskUserId;
import mk.ukim.finki.tasks.domain.valueobjects.Status;
import mk.ukim.finki.tasks.domain.valueobjects.UserId;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskForm {

    @NotNull
    String id;

    @NotNull
    String title;

    @NotNull
    String description;

    @NotNull
    String status = Status.todo.toString();

    @NotNull
    Long duration = 1L;

    @Valid
    @NotNull
    List<Task> dependsOn;

    String userId;

    @NotNull
    LocalDateTime startTime;

    LocalDateTime endTime;

    @Min(0L)
    @Max(1L)
    Double progress = 0.0;

    public TaskForm() {
    }

    public TaskForm(String id, String title, String description, List<Task> dependsOn, String userId, LocalDateTime startTime, LocalDateTime endTime, Long duration,Double progress,String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dependsOn = dependsOn;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.progress=progress;
        this.status=status;
    }
}
