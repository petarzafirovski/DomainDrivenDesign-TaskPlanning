package mk.ukim.finki.users.service;

import mk.ukim.finki.users.domain.model.TaskItem;
import mk.ukim.finki.users.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    List<User> findAll();

    Optional<User> findUserById(String id);

    User save(String name, String surname, String username, String password, Set<TaskItem> tasks);

    void delete(User user);

}
