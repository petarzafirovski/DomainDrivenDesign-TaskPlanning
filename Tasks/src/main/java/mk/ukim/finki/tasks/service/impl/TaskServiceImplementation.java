package mk.ukim.finki.tasks.service.impl;


import lombok.AllArgsConstructor;
import mk.ukim.finki.sharedkernel.domain.events.tasks.TaskCreated;
import mk.ukim.finki.sharedkernel.domain.events.tasks.TaskDeleted;
import mk.ukim.finki.sharedkernel.domain.events.tasks.TaskUpdated;
import mk.ukim.finki.sharedkernel.infra.DomainEventPublisher;
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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskUserRepository taskUserRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(String id) {
        return this.taskRepository.findById(TaskId.of(id));
    }

    @Override
    public List<TaskForm> findAllForms() {
        return this.taskRepository
                .findAll()
                .stream()
                .map(task -> new TaskForm(
                        task.getId().getId()
                        ,task.getTitle(),
                        task.getDescription(),
                        task.getDependsOn(),
                        task.getUser().getId().getId(),
                        task.getStartTime().getTime(),
                        task.getEndTime().getTime(),
                        task.findEstTimeInDays(task.getStartTime(), task.getEndTime()))
                ).collect(Collectors.toList());
    }

    @Override
    public List<Task> findAllByUser(String userId) {
        TaskUserId uId = TaskUserId.of(userId);
        TaskUser taskUser = this.taskUserRepository.findById(uId).get();
        return this.taskRepository.findAllByUser(taskUser);
    }

    @Override
    @Transactional
    public Optional<Task> create(TaskForm taskForm) {
        //TaskUser taskUser = this.taskUserRepository.findById(TaskUserId.of(taskForm.getUserId())).get();
        Task task = Task.build(taskForm.getTitle(),
                taskForm.getDescription(),
                taskForm.getDependsOn(),
                null,
                new Time(taskForm.getStartTime()),
                new Time(taskForm.getEndTime()),
                new Progress(taskForm.getProgress()));

        this.taskRepository.saveAndFlush(task);

        //kafka event for create
        domainEventPublisher.publish(new TaskCreated(task.getId().getId(),null));
        return Optional.of(task);
    }

    @Override
    public void delete(String id) {

        Task taskToDelete = taskRepository.findById(TaskId.of(id)).get();
        taskRepository.findById(TaskId.of(id)).get().getDependsOn().clear();
        getOtherTasks(id).forEach(task -> task.getDependsOn().remove(taskToDelete));

       this.taskRepository.deleteById(TaskId.of(id));

       // domainEventPublisher.publish(new TaskDeleted(taskToDelete.getId().toString(),taskToDelete.getUser().getId().toString()));

    }

    @Override
    @Transactional
    public Optional<Task> update(String id, TaskForm taskForm) {

        Task task = this.taskRepository.findById(TaskId.of(id)).get();

        task.setTitle(taskForm.getTitle() == null ? task.getTitle() : taskForm.getTitle());
        task.setStatus(taskForm.getProgress()==null ? task.getProgress() : new Progress(taskForm.getProgress()));
        task.setDescription(taskForm.getDescription() == null ? task.getDescription() : taskForm.getDescription());
        task.setDependsOn(taskForm.getDependsOn() == null ? task.getDependsOn() : taskForm.getDependsOn());
        task.setStartTime(taskForm.getStartTime() == null ? task.getStartTime() : new Time(taskForm.getStartTime()));
        task.setEndTime(taskForm.getEndTime() == null ? task.getEndTime() : new Time(taskForm.getEndTime()));

        if(taskForm.getUserId() != null){
            TaskUser user = this.taskUserRepository.findById(TaskUserId.of(taskForm.getUserId())).get();
            task.setUser(user);

            //kafka event for update
            domainEventPublisher.publish(new TaskUpdated(task.getId().toString(),taskForm.getUserId().toString()));
        }else{
            TaskUser user = this.taskUserRepository.findById(task.getUser().getId()).get();
            task.setUser(user);
        }

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
