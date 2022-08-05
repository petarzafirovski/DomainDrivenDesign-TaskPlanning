package mk.ukim.finki.tasks.domain.models;

import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;;
import org.springframework.lang.NonNull;

public class TaskUserId extends DomainObjectId {
    private TaskUserId() {
        super(TaskUserId.randomId(TaskUserId.class).getId());
    }

    public TaskUserId(@NonNull String uuid) {
        super(uuid);
    }

    public static TaskUserId of(String uuid) {
        TaskUserId p = new TaskUserId(uuid);
        return p;
    }
}
