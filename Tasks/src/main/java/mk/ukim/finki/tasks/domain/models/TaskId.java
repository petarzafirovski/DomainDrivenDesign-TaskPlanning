package mk.ukim.finki.tasks.domain.models;

import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class TaskId extends DomainObjectId {
    private TaskId() {
        super(TaskId.randomId(TaskId.class).getId());
    }

    public TaskId(@NonNull String uuid) {
        super(uuid);
    }

    public static TaskId of(String uuid) {
        TaskId p = new TaskId(uuid);
        return p;
    }
}
