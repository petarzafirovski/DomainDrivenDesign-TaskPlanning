package mk.ukim.finki.users.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import mk.ukim.finki.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.users.domain.valueobjects.Task;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User extends AbstractEntity<UserId> {

    private String name;
    private String surname;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<TaskItem> taskItemSet = new HashSet<>();

    public User() {

    }

    public User(String name, String surname, String username, String password, Set<TaskItem> tasks) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.taskItemSet = tasks;
    }

    //ova e biznis logika
    public TaskItem addTaskToUser(@NonNull Task task) {
        Objects.requireNonNull(task, "task must not be null");

        TaskItem taskItem = new TaskItem(task.getTaskId(), this);
        this.taskItemSet.add(taskItem);

        return taskItem;
    }

    public void removeTaskFromUser(@NonNull Task task) {
        Objects.requireNonNull(task, "task must not be null");

        TaskItem removed = taskItemSet.stream()
                .filter(t -> t.getTaskId().equals(task.getTaskId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("task does not exist"));
        this.taskItemSet.remove(removed);
    }
}
