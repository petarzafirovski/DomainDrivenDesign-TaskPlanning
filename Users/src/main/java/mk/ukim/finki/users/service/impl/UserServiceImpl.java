package mk.ukim.finki.users.service.impl;

import lombok.RequiredArgsConstructor;
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

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskItemRepository taskItemRepository;

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(String id) {
        return this.userRepository.findById(UserId.of(id));
    }

    @Override
    public User taskItemCreated(String userId, String taskId) {
        User user = this.userRepository.findById(UserId.of(userId)).get();
        TaskItem taskItem = this.taskItemRepository.findById(TaskItemId.of(taskId)).get();

        user.getTaskItemSet().add(taskItem);
        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    public User taskItemDeleted(String userId, String taskId) {
        User user = this.userRepository.findById(UserId.of(userId)).get();
        TaskItem taskItem = this.taskItemRepository.findById(TaskItemId.of(taskId)).get();

        user.getTaskItemSet().remove(taskItem);

        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    public User taskItemUpdated(String userId, String taskId) {
        User user = this.userRepository.findById(UserId.of(userId)).get();
        TaskItem taskItem = this.taskItemRepository.findById(TaskItemId.of(taskId)).get();

       if(!user.getTaskItemSet().contains(taskItem)){
           user.getTaskItemSet().add(taskItem);
       }
        userRepository.saveAndFlush(user);
        return user;
    }
}
