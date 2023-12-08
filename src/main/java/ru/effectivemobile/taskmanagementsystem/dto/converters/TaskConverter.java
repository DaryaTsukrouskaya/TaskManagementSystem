package ru.effectivemobile.taskmanagementsystem.dto.converters;

import org.springframework.stereotype.Component;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;
import ru.effectivemobile.taskmanagementsystem.entities.Task;
import ru.effectivemobile.taskmanagementsystem.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
public class TaskConverter {
    private final UserConverter userConverter;
    private final CommentConverter commentConverter;
    private final UserRepository userRepository;

    public TaskConverter(UserConverter userConverter, CommentConverter commentConverter, UserRepository userRepository) {
        this.userConverter = userConverter;
        this.commentConverter = commentConverter;
        this.userRepository = userRepository;
    }

    public TaskDto toDto(Task task) {
        return Optional.ofNullable(task).map(t -> TaskDto.builder().id(t.getId()).title(t.getTitle())
                .description(t.getDescription()).status(t.getStatus()).priority(t.getPriority()).
                creationDate(t.getCreationDate()).authorId(t.getAuthor().getId()).
                performers(Optional.ofNullable(t.getPerformers()).map(p -> p.stream().map(userConverter::toDto).toList()).orElse(List.of()))
                .comments(Optional.ofNullable(t.getComments()).map(c -> c.stream().map(commentConverter::toDto).toList()).orElse(List.of()))
                .build()).orElse(null);
    }

    public Task fromDto(TaskDto task) {
        return Optional.ofNullable(task).map(t -> Task.builder().id(t.getId()).title(t.getTitle())
                .description(t.getDescription()).status(t.getStatus()).priority(t.getPriority()).
                creationDate(t.getCreationDate()).author(userRepository.findById(t.getAuthorId())).
                performers(Optional.ofNullable(t.getPerformers()).map(p -> p.stream().map(userConverter::fromDto).toList()).orElse(List.of()))
                .comments(Optional.ofNullable(t.getComments()).map(c -> c.stream().map(commentConverter::fromDto).toList()).orElse(List.of()))
                .build()).orElse(null);
    }
}
