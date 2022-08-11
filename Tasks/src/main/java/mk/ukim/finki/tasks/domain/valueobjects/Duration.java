package mk.ukim.finki.tasks.domain.valueobjects;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.temporal.Temporal;

@Embeddable
@Getter
public class Duration {
    private final Long duration;

    protected Duration() {
        this.duration = 0L;
    }

    public Duration(Long duration){
        this.duration = duration;
    }

    public Duration add(Duration duration){
        if(duration != null && duration.duration > 0L){
            return new Duration(duration.duration);
        }else{
            throw new IllegalArgumentException("Duration cannot be less than 0");
        }
    }

    public static java.time.Duration between(Temporal startInclusive, Temporal endExclusive) {
        return java.time.Duration.between(startInclusive,endExclusive);
    }
}
