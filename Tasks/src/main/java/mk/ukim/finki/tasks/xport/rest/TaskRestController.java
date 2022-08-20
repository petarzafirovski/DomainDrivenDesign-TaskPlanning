package mk.ukim.finki.tasks.xport.rest;


import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.domain.valueobjects.Status;
import mk.ukim.finki.tasks.service.TaskService;
import mk.ukim.finki.tasks.service.form.DependencyForm;
import mk.ukim.finki.tasks.service.form.TaskForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/tasks", "/api"})
@CrossOrigin(origins = "http://localhost:3000")
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public List<Task> findAll(@RequestParam(required = false) String filter,
                              @RequestParam(required = false) String userId) {
        if (filter != null) {
            switch (filter) {
                case "non-dependent":
                    return this.taskService.tasksWithoutDependencies();
                case "completed-dependent-tasks":
                    return this.taskService.completedDependentTasks();
                case "non-assigned":
                    return this.taskService.withoutAssignees();
            }
        }
        if (userId != null) {
            return this.taskService.findAllByUser(userId);
        }
        return this.taskService.findAll();
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
    public void saveDependency(@RequestBody DependencyForm dependencyForm){
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