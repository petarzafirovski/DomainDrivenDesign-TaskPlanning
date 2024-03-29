package mk.ukim.finki.users.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.sharedkernel.domain.events.users.UserCreated;
import mk.ukim.finki.sharedkernel.domain.events.users.UserDeleted;
import mk.ukim.finki.sharedkernel.infra.DomainEventPublisher;
import mk.ukim.finki.users.domain.model.TaskItem;
import mk.ukim.finki.users.domain.model.TaskItemId;
import mk.ukim.finki.users.domain.model.User;
import mk.ukim.finki.users.domain.model.UserId;
import mk.ukim.finki.users.domain.repository.TaskItemRepository;
import mk.ukim.finki.users.domain.repository.UserRepository;
import mk.ukim.finki.users.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(String id) {
        return this.userRepository.findById(UserId.of(id));
    }



    public User save(String name, String surname, String username, String password, Set<TaskItem> tasks) {
        User user = this.userRepository.saveAndFlush(new User(name, surname, username, password, tasks));
        domainEventPublisher.publish(new UserCreated(user.getId().getId(), username));
        return user;
    }

    @Override
    public void delete(User user) {
        this.userRepository.delete(user);
        domainEventPublisher.publish(new UserDeleted(user.getId().getId(), user.getUsername()));
    }
}
