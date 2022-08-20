package mk.ukim.finki.tasks.service.form;

import lombok.Data;
import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.domain.models.TaskUserId;
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
    String title;

    @NotNull
    String description;

//    @NotNull
//    String status;

    @Valid
    @NotNull
    List<Task> dependsOn;

    TaskUserId userId;

    @NotNull
    LocalDateTime startTime;

    LocalDateTime endTime;

    @Min(0L)
    @Max(1L)
    Double progress = 0.0;
}
