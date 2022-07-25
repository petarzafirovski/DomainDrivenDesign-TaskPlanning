package mk.ukim.finki.users.domain.model;

import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class TaskItemId extends DomainObjectId {
    private TaskItemId() {
        super(TaskItemId.randomId(TaskItemId.class).getId());
    }

    public TaskItemId(@NonNull String uuid) {
        super(uuid);
    }

    public static TaskItemId of(String uuid) {
        TaskItemId p = new TaskItemId(uuid);
        return p;
    }
}
