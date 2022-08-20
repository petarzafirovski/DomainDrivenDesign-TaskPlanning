package mk.ukim.finki.tasks.service;

import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.service.form.TaskForm;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> findById(String id);

    List<Task> findAll();

    List<Task> findAllByUser(String userId);

    Optional<Task> create(TaskForm taskForm);

    void delete(String id);

    Optional<Task> update(String id, TaskForm taskForm);

    List<Task> getOtherTasks(String id);

    List<Task> withoutAssignees();


    List<Task> tasksWithoutDependencies();

    List<Task> completedDependentTasks();

    void addDependency(String sourceId, String targetId);

    void deleteDependency(String sourceId, String targetId);
}
