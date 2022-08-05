package mk.ukim.finki.users.domain.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.users.domain.valueobjects.TaskId;

import javax.persistence.*;

@Entity
@Table(name = "task_item")
@Getter
public class TaskItem extends AbstractEntity<TaskItemId> {

    @AttributeOverride(name = "id", column = @Column(name = "task_id", nullable = false))
    private TaskId taskId;

    @ManyToOne
    private User user;

    public TaskItem(@NonNull TaskId taskId, @NotNull User user) {
        super(DomainObjectId.randomId(TaskItemId.class));
        this.taskId = taskId;
        this.user = user;
    }

    protected TaskItem() {
        super(DomainObjectId.randomId(TaskItemId.class));
    }
}
