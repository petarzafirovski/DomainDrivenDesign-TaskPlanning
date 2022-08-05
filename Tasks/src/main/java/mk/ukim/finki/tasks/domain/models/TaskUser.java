package mk.ukim.finki.tasks.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.tasks.domain.valueobjects.UserId;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task_user")
@Getter
public class TaskUser extends AbstractEntity<TaskUserId> {

    @AttributeOverride(name = "id", column = @Column(name = "task_user_id", nullable = false))
    private UserId userId;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Task> tasks = new HashSet<>();

    public TaskUser(@NonNull UserId userId) {
        super(DomainObjectId.randomId(TaskUserId.class));
        this.userId = userId;
    }

    protected TaskUser() {
        super(DomainObjectId.randomId(TaskUserId.class));
    }
}
