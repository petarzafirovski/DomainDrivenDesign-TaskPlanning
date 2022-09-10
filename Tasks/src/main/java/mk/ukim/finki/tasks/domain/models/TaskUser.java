package mk.ukim.finki.tasks.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.tasks.domain.valueobjects.UserId;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "task_user")
@Getter
public class TaskUser extends AbstractEntity<TaskUserId> {

//    @AttributeOverride(name = "id", column = @Column(name = "task_user_id", nullable = false))
//    private UserId userId;

    private String username;

    @OneToMany(mappedBy = "user",orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Task> tasks = new HashSet<>();

    public TaskUser() {
        super(DomainObjectId.randomId(TaskUserId.class));
    }

    public TaskUser(String username) {
        super(DomainObjectId.randomId(TaskUserId.class));
        this.username=username;

    }


    public Task addItem(@NonNull Task task) {
        Objects.requireNonNull(task,"task must not be null");
        tasks.add(task);
        return task;
    }

    public void removeItem(@NonNull TaskId taskId) {
        Objects.requireNonNull(taskId,"Task Item must not be null");
        tasks.removeIf(v->v.getId().equals(taskId));
    }

}
