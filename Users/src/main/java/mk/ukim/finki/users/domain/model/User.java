package mk.ukim.finki.users.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import mk.ukim.finki.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User extends AbstractEntity<UserId> {

    private String name;
    private String surname;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<TaskItem> taskItemSet = new HashSet<>();

    public User(){

    }

    public User(String name, String surname, String username, String password, Set<TaskItem> tasks) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.taskItemSet = tasks;
    }
}
