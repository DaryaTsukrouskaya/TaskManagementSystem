package ru.effectivemobile.taskmanagementsystem.dto.converters;

import org.springframework.stereotype.Component;
import ru.effectivemobile.taskmanagementsystem.dto.CommentDto;
import ru.effectivemobile.taskmanagementsystem.entities.Comment;
import ru.effectivemobile.taskmanagementsystem.repositories.TaskRepository;
import ru.effectivemobile.taskmanagementsystem.repositories.UserRepository;

import java.util.Optional;

@Component
public class CommentConverter {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public CommentConverter(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public CommentDto toDto(Comment comment) {
        return Optional.ofNullable(comment).map(c -> CommentDto.builder().id(c.getId()).commentText(c.getCommentText())
                .creationDate(c.getCreationDate()).authorId(c.getUser().getId()).taskId(c.getTask().getId()).build()).orElse(null);
    }

    public Comment fromDto(CommentDto comment) {
        return Optional.ofNullable(comment).map(c -> Comment.builder().id(c.getId()).commentText(c.getCommentText()).
                creationDate(c.getCreationDate()).
                user(userRepository.findById(c.getAuthorId())).task(taskRepository.findById(c.getTaskId())).build()).orElse(null);
    }
}
