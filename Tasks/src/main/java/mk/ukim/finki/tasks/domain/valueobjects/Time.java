package mk.ukim.finki.tasks.domain.valueobjects;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class Time {
    private final LocalDateTime time;

    protected Time(){
        this.time = LocalDateTime.now();
    }

    public Time(LocalDateTime date){
        this.time=date;
    }
}
