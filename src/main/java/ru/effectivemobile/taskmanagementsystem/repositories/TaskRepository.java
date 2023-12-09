package ru.effectivemobile.taskmanagementsystem.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.taskmanagementsystem.entities.Task;

import java.util.List;

@Transactional
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
    Task findById(int id);

    List<Task> findByAuthorId(int id, Pageable pageable);

    @Query("select t from Task t join User u where u.id =: performerId")
    List<Task> findByPerformers(@Param("performerId") int id, Pageable pageable);
}
