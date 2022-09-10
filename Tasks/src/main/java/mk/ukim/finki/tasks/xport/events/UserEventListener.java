package mk.ukim.finki.tasks.xport.events;

import lombok.AllArgsConstructor;
import mk.ukim.finki.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.sharedkernel.domain.events.users.UserCreated;
import mk.ukim.finki.sharedkernel.domain.events.users.UserDeleted;
import mk.ukim.finki.tasks.service.TaskUserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserEventListener {
    TaskUserService userService;

    @KafkaListener(topics = TopicHolder.TOPIC_USER_CREATED, groupId = "tasks")
    public void consumeUserCreated(String jsonMessage) {
        try {
            UserCreated event = DomainEvent.fromJson(jsonMessage, UserCreated.class);
            userService.userCreated(event.getUserId(), event.getUsername());
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    @KafkaListener(topics = TopicHolder.TOPIC_USER_DELETED, groupId = "tasks")
    public void consumeUserDeleted(String jsonMessage) {
        try {
            UserDeleted event = DomainEvent.fromJson(jsonMessage, UserDeleted.class);
            userService.userDeleted(event.getUserId(), event.getUsername());
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
}
