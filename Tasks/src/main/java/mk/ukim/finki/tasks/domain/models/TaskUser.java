package mk.ukim.finki.tasks.domain.models;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.tasks.domain.valueobjects.UserId;


import javax.persistence.*;

@Entity
@Table(name = "task_user")
@Getter
public class TaskUser extends AbstractEntity<TaskUserId> {
    @AttributeOverride(name = "id", column = @Column(name = "task_user_id", nullable = false))
    private UserId userId;

    public TaskUser(@NonNull UserId userId) {
        super(DomainObjectId.randomId(TaskUserId.class));
        this.userId = userId;
    }

    protected TaskUser() {
        super(DomainObjectId.randomId(TaskUserId.class));
    }
}
