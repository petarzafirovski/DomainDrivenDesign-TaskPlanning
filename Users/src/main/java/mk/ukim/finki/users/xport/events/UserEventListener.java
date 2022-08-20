package mk.ukim.finki.users.xport.events;

import lombok.AllArgsConstructor;
import mk.ukim.finki.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.sharedkernel.domain.events.tasks.TaskCreated;
import mk.ukim.finki.sharedkernel.domain.events.tasks.TaskDeleted;
import mk.ukim.finki.sharedkernel.domain.events.tasks.TaskUpdated;
import mk.ukim.finki.users.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserEventListener {
    private final UserService userService;

    @KafkaListener(topics= TopicHolder.TOPIC_TASK_CREATED, groupId = "users")
    public void consumeTaskItemCreated(String jsonMessage) {
        try {
            TaskCreated event = DomainEvent.fromJson(jsonMessage,TaskCreated.class);
            userService.taskItemCreated(event.getUserId(),event.getTaskId());
        } catch (Exception e){
            throw new IllegalStateException();
        }

    }

    @KafkaListener(topics= TopicHolder.TOPIC_TASK_UPDATED, groupId = "users")
    public void consumeTaskItemUpdated(String jsonMessage) {
        try {
            TaskUpdated event = DomainEvent.fromJson(jsonMessage,TaskUpdated.class);
            userService.taskItemUpdated(event.getUserId(),event.getTaskId());
        } catch (Exception e){
            throw new IllegalStateException();
        }

    }

    @KafkaListener(topics= TopicHolder.TOPIC_TASK_DELETED, groupId = "users")
    public void consumeTaskItemDeleted(String jsonMessage) {
        try {
            TaskDeleted event = DomainEvent.fromJson(jsonMessage, TaskDeleted.class);
            userService.taskItemDeleted(event.getUserId(),event.getTaskId());
        } catch (Exception e){
            throw new IllegalStateException();
        }

    }
}
