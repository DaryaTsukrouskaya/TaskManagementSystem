package ru.effectivemobile.taskmanagementsystem.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.taskmanagementsystem.entities.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;
@Transactional
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByTaskId(int id, Pageable pageable);
}
