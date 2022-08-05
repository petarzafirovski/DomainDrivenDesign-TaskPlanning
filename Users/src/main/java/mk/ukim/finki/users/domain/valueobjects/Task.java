package mk.ukim.finki.users.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import mk.ukim.finki.sharedkernel.domain.base.ValueObject;

@Getter
public class Task implements ValueObject {

    private final TaskId taskId;

    public Task() {
        this.taskId = TaskId.randomId(TaskId.class);
    }

    @JsonCreator
    public Task(TaskId taskId) {
        this.taskId = taskId;
    }
}
