package mk.ukim.finki.tasks.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.domain.models.TaskUser;
import mk.ukim.finki.tasks.service.TaskUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/users", "/api"})
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class TaskUserController {

    private final TaskUserService userService;

    @GetMapping
    public List<TaskUser> getTaskUsers(){
        return this.userService.getAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<TaskUser> findByUsername(@PathVariable String username) {
        return this.userService.getUserByUserName(username)
                .map(taskUser -> ResponseEntity.ok().body(taskUser))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
