package mk.ukim.finki.tasks.service;

import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.domain.models.TaskId;
import mk.ukim.finki.tasks.domain.models.TaskUserId;
import mk.ukim.finki.tasks.service.form.TaskForm;


import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> findById(TaskId id);

    List<Task> findAll();

    List<Task> findAllByUser(TaskUserId userId);

    Optional<Task> create(TaskForm taskForm);

    void delete(TaskId id);

    Optional<Task> update(TaskId id, TaskForm taskForm);

    List<Task> getOtherTasks(TaskId id);

    List<Task> withoutAssignees();

    Long findEstTimeInHours(Task task);

    List<Task> tasksWithoutDependencies();

    List<Task> completedDependentTasks();

    void addDependency(TaskId sourceId, TaskId targetId);

    void deleteDependency(TaskId sourceId, TaskId targetId);
}
