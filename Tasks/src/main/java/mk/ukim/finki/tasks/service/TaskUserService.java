package mk.ukim.finki.tasks.service;

import mk.ukim.finki.tasks.domain.models.TaskUser;


public interface TaskUserService {
    TaskUser userCreated(String userId, String username);
    void userDeleted(String userId, String username);
}
