package mk.ukim.finki.sharedkernel.domain.events.users;

import lombok.Getter;
import mk.ukim.finki.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.sharedkernel.domain.events.DomainEvent;

@Getter
public class UserDeleted extends DomainEvent {

    private String userId;
    private String username;

    public UserDeleted() {
        super(TopicHolder.TOPIC_USER_DELETED);
    }

    public UserDeleted(String userId, String username) {
        super(TopicHolder.TOPIC_USER_DELETED);
        this.userId = userId;
        this.username = username;
    }

}
