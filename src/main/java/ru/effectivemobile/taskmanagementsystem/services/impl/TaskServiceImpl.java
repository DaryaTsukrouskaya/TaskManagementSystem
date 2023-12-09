package ru.effectivemobile.taskmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.converters.TaskConverter;
import ru.effectivemobile.taskmanagementsystem.dto.converters.UserConverter;
import ru.effectivemobile.taskmanagementsystem.entities.Task;
import ru.effectivemobile.taskmanagementsystem.exceptions.InsufficientRightsException;
import ru.effectivemobile.taskmanagementsystem.repositories.TaskRepository;
import ru.effectivemobile.taskmanagementsystem.services.AuthService;
import ru.effectivemobile.taskmanagementsystem.services.TaskService;
import ru.effectivemobile.taskmanagementsystem.services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskConverter taskConverter;
    private final TaskRepository taskRepository;
    private final AuthService authService;
    private final UserConverter userConverter;
    private final UserService userService;

    public TaskDto createTask(CreateTaskDto taskDto) {
        Task newTask = Task.builder().id(0).title(taskDto.getTitle()).description(taskDto.getDescription())
                .status(taskDto.getStatus()).priority(taskDto.getPriority()).creationDate(LocalDate.now()).
                author(authService.getPrincipal().map(userConverter::fromDto).orElse(null))
                .performers(userService.getPerformers(taskDto.getPerformers())).comments(null).build();
        taskRepository.save(newTask);
        return taskConverter.toDto(newTask);
    }

    public void deleteTask(int id) throws InsufficientRightsException {
        Task task = taskRepository.findById(id);
        if (task.getAuthor().getEmail().equals(authService.getPrincipal().get().getEmail())) {
            taskRepository.delete(task);
        } else {
            throw new InsufficientRightsException("Вы не можете удалять задачи, автором которых вы не являетесь.");
        }
    }

    public List<TaskDto> userAssignedTasks(int id) {
        return Optional.ofNullable(taskRepository.findByAuthorId(id)).map(t -> t.stream().map(taskConverter::toDto).toList())
                .orElse(null);
    }

    public List<TaskDto> userPerformedTasks(int id) {
        return userService.findUser(id).getPerformedTasks();
    }
}
