package mk.ukim.finki.users.service;

import mk.ukim.finki.users.domain.model.User;
import mk.ukim.finki.users.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findUserById(UserId id);
}
