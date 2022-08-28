package mk.ukim.finki.users.xport.rest;


import mk.ukim.finki.users.domain.model.User;
import mk.ukim.finki.users.service.UserService;

import mk.ukim.finki.users.service.form.UserForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000/")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserForm> listUsers(){
        return this.userService.findAll()
                .stream()
                .map(user -> new UserForm(user.getId().getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        return this.userService.findUserById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
