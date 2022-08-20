package mk.ukim.finki.sharedkernel.infra;

import mk.ukim.finki.sharedkernel.domain.events.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
