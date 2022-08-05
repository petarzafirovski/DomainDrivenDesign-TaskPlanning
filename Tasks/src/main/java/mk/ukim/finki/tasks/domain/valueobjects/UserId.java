package mk.ukim.finki.tasks.domain.valueobjects;

import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.tasks.domain.models.TaskUserId;
import org.springframework.lang.NonNull;

public class UserId extends DomainObjectId {
    public UserId() {
        super(TaskUserId.randomId(TaskUserId.class).getId());
    }

    protected UserId(@NonNull String uuid) {
        super(uuid);
    }
}