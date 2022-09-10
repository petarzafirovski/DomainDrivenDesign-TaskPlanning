package mk.ukim.finki.sharedkernel.domain.events.users;

import lombok.Getter;
import mk.ukim.finki.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.sharedkernel.domain.events.DomainEvent;

@Getter
public class UserCreated extends DomainEvent {

    private String userId;
    private String username;

    public UserCreated() {
        super(TopicHolder.TOPIC_USER_CREATED);
    }

    public UserCreated(String userId, String username) {
        super(TopicHolder.TOPIC_USER_CREATED);
        this.userId = userId;
        this.username = username;
    }

}
