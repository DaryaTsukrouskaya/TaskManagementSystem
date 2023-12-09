package ru.effectivemobile.taskmanagementsystem.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.taskmanagementsystem.entities.Task;

import java.util.List;

@Transactional
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findById(int id);

    List<Task> findByAuthorId(int id);
}
