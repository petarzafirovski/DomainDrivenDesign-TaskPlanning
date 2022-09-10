package mk.ukim.finki.tasks.service;

import mk.ukim.finki.tasks.domain.models.TaskUser;

import java.util.List;
import java.util.Optional;


public interface TaskUserService {
    TaskUser userCreated(String userId, String username);
    void userDeleted(String userId, String username);
    List<TaskUser> getAll();
    Optional<TaskUser> getUserByUserName(String username);
}
