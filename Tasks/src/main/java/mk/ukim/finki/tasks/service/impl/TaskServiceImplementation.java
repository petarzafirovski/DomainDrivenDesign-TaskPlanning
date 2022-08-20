package mk.ukim.finki.tasks.service.impl;


import lombok.AllArgsConstructor;
import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.domain.models.TaskId;
import mk.ukim.finki.tasks.domain.models.TaskUser;
import mk.ukim.finki.tasks.domain.models.TaskUserId;
import mk.ukim.finki.tasks.domain.repository.TaskRepository;
import mk.ukim.finki.tasks.domain.repository.TaskUserRepository;
import mk.ukim.finki.tasks.domain.valueobjects.Progress;
import mk.ukim.finki.tasks.domain.valueobjects.Status;
import mk.ukim.finki.tasks.domain.valueobjects.Time;
import mk.ukim.finki.tasks.domain.valueobjects.UserId;
import mk.ukim.finki.tasks.service.TaskService;
import mk.ukim.finki.tasks.service.form.TaskForm;
import mk.ukim.finki.users.domain.model.User;
import mk.ukim.finki.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskUserRepository taskUserRepository;


    @Override
    public Optional<Task> findById(TaskId id) {
        return this.taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    @Override
    public List<Task> findAllByUser(UserId userId) {
        TaskUser taskUser = this.taskUserRepository.findById(userId).get();
        return this.taskRepository.findAllByUser(taskUser);
    }

    @Override
    public Optional<Task> create(TaskForm taskForm) {
        TaskUser taskUser = this.taskUserRepository.findById(taskForm.getUserId()).get();
        Task task = Task.build(taskForm.getTitle(),taskForm.getDescription(),taskForm.getDependsOn(),taskUser,new Time(taskForm.getStartTime()),new Time(taskForm.getEndTime()),new Progress(taskForm.getProgress()));
        this.taskRepository.saveAndFlush(task);
        return Optional.of(task);
    }

    @Override
    public void delete(TaskId id) {
        this.taskRepository.deleteById(id);
    }

    @Override
    public Optional<Task> update(TaskId id, TaskForm taskForm) {

        Task task = this.taskRepository.findById(id).get();

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
    public List<Task> getOtherTasks(TaskId id) {
        return this.taskRepository.findAll()
                .stream()
                .filter(task -> !task.getId().equals(id))
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
        TaskId sourceTaskId= new TaskId(sourceId);
        Task sourceTask = this.taskRepository.findById(sourceTaskId)
                .orElseThrow(IllegalArgumentException::new);

        TaskId targetTaskId= new TaskId(targetId);
        Task targetTask = this.taskRepository.findById(targetTaskId)
                .orElseThrow(IllegalArgumentException::new);

        //target task e taa sto treba da zavisi od source task
        if(!targetTask.getDependsOn().contains(sourceTask)){
            targetTask.getDependsOn().add(sourceTask);
            taskRepository.save(targetTask);
        }
    }

    @Override
    public void deleteDependency(String sourceId, String targetId) {
        TaskId sourceTaskId= new TaskId(sourceId);
        Task sourceTask = this.taskRepository.findById(sourceTaskId)
                .orElseThrow(IllegalArgumentException::new);

        TaskId targetTaskId= new TaskId(targetId);
        Task targetTask = this.taskRepository.findById(targetTaskId)
                .orElseThrow(IllegalArgumentException::new);

        if(targetTask.getDependsOn().contains(sourceTask)){
            targetTask.getDependsOn().remove(sourceTask);
            taskRepository.save(targetTask);
        }
    }
}
