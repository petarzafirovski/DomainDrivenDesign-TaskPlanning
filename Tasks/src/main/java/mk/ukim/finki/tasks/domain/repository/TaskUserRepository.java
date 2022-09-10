package mk.ukim.finki.tasks.domain.repository;

import mk.ukim.finki.tasks.domain.models.TaskUser;
import mk.ukim.finki.tasks.domain.models.TaskUserId;
import mk.ukim.finki.tasks.domain.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskUserRepository extends JpaRepository<TaskUser, TaskUserId> {

    Optional<TaskUser> findByUsername(String username);
}
