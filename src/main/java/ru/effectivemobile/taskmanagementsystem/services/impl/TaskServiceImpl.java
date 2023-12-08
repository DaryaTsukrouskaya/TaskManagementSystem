package ru.effectivemobile.taskmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.converters.TaskConverter;
import ru.effectivemobile.taskmanagementsystem.dto.converters.UserConverter;
import ru.effectivemobile.taskmanagementsystem.entities.Task;
import ru.effectivemobile.taskmanagementsystem.repositories.TaskRepository;
import ru.effectivemobile.taskmanagementsystem.services.AuthService;
import ru.effectivemobile.taskmanagementsystem.services.TaskService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskConverter taskConverter;
    private final TaskRepository taskRepository;
    private final AuthService authService;
    private final UserConverter userConverter;
    private final UserServiceImpl userService;

    public TaskDto createTask(CreateTaskDto taskDto) {
        Task newTask = Task.builder().id(0).title(taskDto.getTitle()).description(taskDto.getDescription())
                .status(taskDto.getStatus()).priority(taskDto.getPriority()).creationDate(LocalDate.now()).
                author(authService.getPrincipal().map(userConverter::fromDto).orElse(null))
                .performers(userService.getPerformers(taskDto.getPerformers())).comments(null).build();
        taskRepository.save(newTask);
        return taskConverter.toDto(newTask);
    }
}
