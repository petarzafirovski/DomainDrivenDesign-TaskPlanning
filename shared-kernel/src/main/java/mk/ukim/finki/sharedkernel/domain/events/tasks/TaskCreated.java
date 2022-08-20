package mk.ukim.finki.sharedkernel.domain.events.tasks;

import lombok.Getter;
import mk.ukim.finki.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.sharedkernel.domain.events.DomainEvent;

@Getter
public class TaskCreated  extends DomainEvent {

    private String taskId;
    private String userId;

    public TaskCreated(String topic) {
        super(TopicHolder.TOPIC_TASK_CREATED);
    }

    public TaskCreated(String taskId, String userId) {
        super(TopicHolder.TOPIC_TASK_CREATED);
        this.taskId = taskId;
        this.userId = userId;
    }

}
