package mk.ukim.finki.tasks.service;

import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.domain.models.TaskId;
import mk.ukim.finki.tasks.domain.models.TaskUserId;
import mk.ukim.finki.tasks.domain.valueobjects.UserId;
import mk.ukim.finki.tasks.service.form.TaskForm;


import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> findById(TaskId id);

    List<Task> findAll();

    List<Task> findAllByUser(UserId userId);

    Optional<Task> create(TaskForm taskForm);

    void delete(TaskId id);

    Optional<Task> update(TaskId id, TaskForm taskForm);

    List<Task> getOtherTasks(TaskId id);

    List<Task> withoutAssignees();


    List<Task> tasksWithoutDependencies();

    List<Task> completedDependentTasks();

    void addDependency(String sourceId, String targetId);

    void deleteDependency(String sourceId, String targetId);
}
