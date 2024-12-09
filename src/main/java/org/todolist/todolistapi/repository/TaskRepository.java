package org.todolist.todolistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todolist.todolistapi.model.Task;
import org.todolist.todolistapi.model.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByUserAndCompleted(User user, boolean completed);
}
