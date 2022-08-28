package mk.ukim.finki.sharedkernel.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;


import java.time.Instant;


@Getter
public class DomainEvent {

    private String topic;
    //private Instant occurredOn;

    public DomainEvent(String topic) {
        //this.occurredOn = Instant.now();
        this.topic = topic;
    }

    public String toJson() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
          json = ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
        }
        return json;
    }

    public String topic() {
        return topic;
    }

    public static <E extends DomainEvent> E fromJson(String json, Class<E> eventClass) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json,eventClass);
    }
}