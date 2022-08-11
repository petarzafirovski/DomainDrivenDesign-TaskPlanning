package mk.ukim.finki.users.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.users.domain.model.User;
import mk.ukim.finki.users.domain.model.UserId;
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

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(UserId id) {
        return this.userRepository.findById(id);
    }
}
