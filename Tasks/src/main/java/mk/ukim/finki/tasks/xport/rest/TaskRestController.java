package mk.ukim.finki.tasks.xport.rest;


import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.domain.valueobjects.Status;
import mk.ukim.finki.tasks.service.TaskService;
import mk.ukim.finki.tasks.service.form.DependencyForm;
import mk.ukim.finki.tasks.service.form.TaskForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"/api/tasks", "/api"})
@CrossOrigin(origins = "http://localhost:3000")
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public List<TaskForm> findAll(@RequestParam(required = false) String filter,
                                  @RequestParam(required = false) String userId) {
        List<Task> tasks = new ArrayList<>();
        if (filter != null) {
            switch (filter) {
                case "non-dependent":
                    tasks = this.taskService.tasksWithoutDependencies();
                    break;
                case "completed-dependent-tasks":
                    tasks = this.taskService.completedDependentTasks();
                    break;
                case "non-assigned":
                    tasks = this.taskService.withoutAssignees();
                    break;
            }
        }
        if (userId != null) {
            tasks = this.taskService.findAllByUser(userId);
        }
        tasks = this.taskService.findAll();
        return tasks.stream()
                .map(task -> new TaskForm(
                        task.getId().getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getDependsOn(),
                        task.getUser() == null ?  "0" : task.getUser().getId().getId() ,
                        task.getStartTime().getTime(),
                        task.getEndTime().getTime(),
                        task.findEstTimeInDays(task.getStartTime(), task.getEndTime()))
                ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable String id) {
        return this.taskService.findById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/status")
    public List<Status> getStatuses() {
        return List.of(Status.values());
    }


    @PostMapping("/add-task")
    public ResponseEntity<Task> save(@RequestBody TaskForm taskForm) {
        return this.taskService.create(taskForm)
                .map(
                        task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit-task/{id}")
    public ResponseEntity<Task> save(@PathVariable String id,
                                     @RequestBody TaskForm taskForm) {

        return this.taskService.update(id, taskForm).map(
                        task -> ResponseEntity.ok().body(task)
                )
                .orElseGet(() -> ResponseEntity.badRequest().build());


    }

    @PutMapping("addDependency")
    public void saveDependency(@RequestBody DependencyForm dependencyForm) {
        this.taskService.addDependency(dependencyForm.getSourceId(), dependencyForm.getTargetId());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        this.taskService.delete(id);
        if (this.taskService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/deleteDependency")
    public void deleteById(@RequestBody DependencyForm dependencyForm) {
        this.taskService.deleteDependency(dependencyForm.getSourceId(), dependencyForm.getTargetId());
    }

}