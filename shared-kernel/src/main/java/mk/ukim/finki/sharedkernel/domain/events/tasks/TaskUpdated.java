package mk.ukim.finki.sharedkernel.domain.events.tasks;

import lombok.Getter;
import mk.ukim.finki.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.sharedkernel.domain.events.DomainEvent;

@Getter
public class TaskUpdated extends DomainEvent {

    private String taskId;
    private String userId;

    public TaskUpdated(String topic) {
        super(TopicHolder.TOPIC_TASK_UPDATED);
    }

    public TaskUpdated(String taskId, String userId) {
        super(TopicHolder.TOPIC_TASK_UPDATED);
        this.taskId=taskId;
        this.userId = userId;
    }
}
