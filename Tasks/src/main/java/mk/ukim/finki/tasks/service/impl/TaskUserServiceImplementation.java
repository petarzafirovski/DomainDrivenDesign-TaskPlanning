package mk.ukim.finki.tasks.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.tasks.domain.models.TaskUser;
import mk.ukim.finki.tasks.domain.repository.TaskUserRepository;
import mk.ukim.finki.tasks.service.TaskUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class TaskUserServiceImplementation implements TaskUserService {

    private final TaskUserRepository taskUserRepository;

    @Override
    public TaskUser userCreated(String userId, String username) {
        if (this.taskUserRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("username exists");
        }
        TaskUser user = new TaskUser(username);
        return this.taskUserRepository.save(user);
    }

    @Override
    public void userDeleted(String userId, String username) {
        TaskUser user = this.taskUserRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("username doesn't exist"));
        this.taskUserRepository.delete(user);
    }

    @Override
    public List<TaskUser> getAll() {
        return this.taskUserRepository.findAll();
    }

    @Override
    public Optional<TaskUser> getUserByUserName(String username) {
        return Optional.of(this.taskUserRepository.findByUsername(username).get());
    }
}
