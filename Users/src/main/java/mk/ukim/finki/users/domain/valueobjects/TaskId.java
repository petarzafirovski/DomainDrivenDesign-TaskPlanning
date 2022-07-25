package mk.ukim.finki.users.domain.valueobjects;

import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.users.domain.model.TaskItemId;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

@Embeddable
public class TaskId extends DomainObjectId {

    public TaskId() {
        super(TaskItemId.randomId(TaskItemId.class).getId());
    }

    protected TaskId(@NonNull String uuid) {
        super(uuid);
    }

}
