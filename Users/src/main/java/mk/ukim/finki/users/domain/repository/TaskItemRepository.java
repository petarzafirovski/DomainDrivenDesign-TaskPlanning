package mk.ukim.finki.users.domain.repository;

import mk.ukim.finki.users.domain.model.TaskItem;
import mk.ukim.finki.users.domain.model.TaskItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskItemRepository extends JpaRepository<TaskItem, TaskItemId> {
}
