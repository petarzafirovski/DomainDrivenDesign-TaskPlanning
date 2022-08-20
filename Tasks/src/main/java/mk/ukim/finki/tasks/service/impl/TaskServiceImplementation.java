package mk.ukim.finki.tasks.service.impl;


import lombok.AllArgsConstructor;
import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.domain.models.TaskId;
import mk.ukim.finki.tasks.domain.models.TaskUser;
import mk.ukim.finki.tasks.domain.repository.TaskRepository;
import mk.ukim.finki.tasks.domain.repository.TaskUserRepository;
import mk.ukim.finki.tasks.domain.valueobjects.Progress;
import mk.ukim.finki.tasks.domain.valueobjects.Status;
import mk.ukim.finki.tasks.domain.valueobjects.Time;
import mk.ukim.finki.tasks.domain.valueobjects.UserId;
import mk.ukim.finki.tasks.service.TaskService;
import mk.ukim.finki.tasks.service.form.TaskForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskUserRepository taskUserRepository;


    @Override
    public Optional<Task> findById(String id) {
        return this.taskRepository.findById(TaskId.of(id));
    }

    @Override
    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    @Override
    public List<Task> findAllByUser(String userId) {
        UserId uId = UserId.of(userId);
        TaskUser taskUser = this.taskUserRepository.findById(uId).get();
        return this.taskRepository.findAllByUser(taskUser);
    }

    @Override
    public Optional<Task> create(TaskForm taskForm) {
        TaskUser taskUser = this.taskUserRepository.findById(taskForm.getUserId()).get();
        Task task = Task.build(taskForm.getTitle(),
                taskForm.getDescription(),
                taskForm.getDependsOn(),
                taskUser,
                new Time(taskForm.getStartTime()),
                new Time(taskForm.getEndTime()),
                new Progress(taskForm.getProgress()));

        this.taskRepository.saveAndFlush(task);
        return Optional.of(task);
    }

    @Override
    public void delete(String id) {

        Task taskToDelete = taskRepository.findById(TaskId.of(id)).get();
        taskRepository.findById(TaskId.of(id)).get().getDependsOn().clear();
        getOtherTasks(id).forEach(task -> task.getDependsOn().remove(taskToDelete));

        this.taskRepository.deleteById(TaskId.of(id));
    }

    @Override
    public Optional<Task> update(String id, TaskForm taskForm) {

        Task task = this.taskRepository.findById(TaskId.of(id)).get();

        task.setTitle(taskForm.getTitle() == null ? task.getTitle() : taskForm.getTitle());
        task.setStatus(taskForm.getProgress()==null ? task.getProgress() : new Progress(taskForm.getProgress()));
        task.setDescription(taskForm.getDescription() == null ? task.getDescription() : taskForm.getDescription());
        task.setDependsOn(taskForm.getDependsOn() == null ? task.getDependsOn() : taskForm.getDependsOn());
        task.setStartTime(taskForm.getStartTime() == null ? task.getStartTime() : new Time(taskForm.getStartTime()));
        task.setEndTime(taskForm.getEndTime() == null ? task.getEndTime() : new Time(taskForm.getEndTime()));

        this.taskRepository.saveAndFlush(task);
        return Optional.of(task);
    }

    @Override
    public List<Task> getOtherTasks(String id) {
        return this.taskRepository.findAll()
                .stream()
                .filter(task -> !task.getId().equals(TaskId.of(id)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> withoutAssignees() {
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getUser() == null)
                .collect(Collectors.toList());
    }


    @Override
    public List<Task> tasksWithoutDependencies() {
        return this.taskRepository.findAll().stream().filter(task -> task.getDependsOn().size() == 0).collect(Collectors.toList());
    }

    @Override
    public List<Task> completedDependentTasks() {
        return this.taskRepository.findAll().stream().
                filter(task -> task.getStatus().toString().equals(Status.finished.toString())
                        && !task.getDependsOn().isEmpty()).
                collect(Collectors.toList());
    }

    @Override
    public void addDependency(String sourceId, String targetId) {
        Task sourceTask = this.taskRepository.findById(TaskId.of(sourceId))
                .orElseThrow(IllegalArgumentException::new);

        Task targetTask = this.taskRepository.findById(TaskId.of(targetId))
                .orElseThrow(IllegalArgumentException::new);

        //target task e taa sto treba da zavisi od source task
        if(!targetTask.getDependsOn().contains(sourceTask)){
            targetTask.getDependsOn().add(sourceTask);
            taskRepository.save(targetTask);
        }
    }

    @Override
    public void deleteDependency(String sourceId, String targetId) {
        Task sourceTask = this.taskRepository.findById(TaskId.of(sourceId))
                .orElseThrow(IllegalArgumentException::new);

        Task targetTask = this.taskRepository.findById(TaskId.of(targetId))
                .orElseThrow(IllegalArgumentException::new);

        if(targetTask.getDependsOn().contains(sourceTask)){
            targetTask.getDependsOn().remove(sourceTask);
            taskRepository.save(targetTask);
        }
    }
}
