package mk.ukim.finki.sharedkernel.domain.events.tasks;

import lombok.Getter;
import mk.ukim.finki.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.sharedkernel.domain.events.DomainEvent;

@Getter
public class TaskDeleted extends DomainEvent {
    private String taskId;
    private String userId;

    public TaskDeleted(String topic) {
        super(TopicHolder.TOPIC_TASK_DELETED);
    }

    public TaskDeleted(String taskId, String userId) {
        super(TopicHolder.TOPIC_TASK_DELETED);
        this.taskId = taskId;
        this.userId = userId;
    }
}
