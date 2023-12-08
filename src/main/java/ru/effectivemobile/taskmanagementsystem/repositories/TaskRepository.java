package ru.effectivemobile.taskmanagementsystem.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.taskmanagementsystem.entities.Task;

@Transactional
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findById(int id);
}
