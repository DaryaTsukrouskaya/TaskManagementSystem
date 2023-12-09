package ru.effectivemobile.taskmanagementsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.taskmanagementsystem.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findByUserId(int id);
    Comment findByTaskId (int id);
}
