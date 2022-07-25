package mk.ukim.finki.users.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.users.domain.valueobjects.TaskId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "task_item")
@Getter
public class TaskItem extends AbstractEntity<TaskItemId> {

    @AttributeOverride(name = "id", column = @Column(name = "task_id", nullable = false))
    private TaskId taskId;

    public TaskItem(@NonNull TaskId taskId){
        super(DomainObjectId.randomId(TaskItemId.class));
        this.taskId =taskId;
    }

    protected TaskItem(){
        super(DomainObjectId.randomId(TaskItemId.class));
    }
}
