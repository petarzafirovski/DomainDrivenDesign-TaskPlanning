package mk.ukim.finki.users.domain.repository;

import mk.ukim.finki.users.domain.model.User;
import mk.ukim.finki.users.domain.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
}
