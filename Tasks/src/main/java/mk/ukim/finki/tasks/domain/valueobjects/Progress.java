package mk.ukim.finki.tasks.domain.valueobjects;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Progress {
    private final Double progress;

    protected Progress(){
        this.progress = 0.0;
    }
}
