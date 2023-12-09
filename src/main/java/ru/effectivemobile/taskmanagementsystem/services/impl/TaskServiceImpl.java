package ru.effectivemobile.taskmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.SearchParamsDto;
import ru.effectivemobile.taskmanagementsystem.dto.StatusDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.UpdateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.converters.TaskConverter;
import ru.effectivemobile.taskmanagementsystem.dto.converters.UserConverter;
import ru.effectivemobile.taskmanagementsystem.entities.Task;
import ru.effectivemobile.taskmanagementsystem.entities.User;
import ru.effectivemobile.taskmanagementsystem.exceptions.InsufficientRightsException;
import ru.effectivemobile.taskmanagementsystem.repositories.TaskRepository;
import ru.effectivemobile.taskmanagementsystem.repositories.TaskSearchSpecification;
import ru.effectivemobile.taskmanagementsystem.services.AuthService;
import ru.effectivemobile.taskmanagementsystem.services.TaskService;
import ru.effectivemobile.taskmanagementsystem.services.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskConverter taskConverter;
    private final TaskRepository taskRepository;
    private final AuthService authService;
    private final UserConverter userConverter;
    private final UserService userService;

    @Override
    public TaskDto createTask(CreateTaskDto taskDto) {
        Task newTask = Task.builder().id(0).title(taskDto.getTitle()).description(taskDto.getDescription())
                .status(taskDto.getStatus()).priority(taskDto.getPriority()).creationDate(LocalDate.now()).
                author(authService.getPrincipal().map(userConverter::fromDto).orElse(null))
                .performers(userService.findPerformers(taskDto.getPerformers())).comments(null).build();
        taskRepository.save(newTask);
        return taskConverter.toDto(newTask);
    }

    @Override
    public void deleteTask(int id) throws InsufficientRightsException {
        Task task = taskRepository.findById(id);
        if (task.getAuthor().getEmail().equals(authService.getPrincipal().get().getEmail())) {
            taskRepository.delete(task);
        } else {
            throw new InsufficientRightsException("Вы не можете удалять задачи, автором которых вы не являетесь.");
        }
    }

    @Override
    public List<TaskDto> assignedTasks(int id, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("priority").ascending());
        List<Task> tasks = taskRepository.findByAuthorId(id, pageable);
        return tasks.stream().map(taskConverter::toDto).toList();
    }

    @Override
    public List<TaskDto> performedTasks(int id, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("priority").ascending());
        List<Task> tasks = taskRepository.findByPerformers(id, pageable);
        return tasks.stream().map(taskConverter::toDto).toList();
    }

    @Override
    public TaskDto changeStatus(int taskId, StatusDto newStatus) throws InsufficientRightsException {
        Task task = taskRepository.findById(taskId);
        User user = task.getPerformers().stream().filter(p -> p.getId() == taskId).findFirst().orElse(null);
        if (user != null) {
            task.setStatus(newStatus.getStatus());
            taskRepository.save(task);
            return taskConverter.toDto(task);
        } else {
            throw new InsufficientRightsException("Вы не можете менять задачи, автором или исполнителем которых вы не являетесь.");
        }
    }

    @Override
    public TaskDto addPerformers(int taskId, List<String> newPerformers) throws InsufficientRightsException {
        Task task = taskRepository.findById(taskId);
        if (task.getAuthor().getEmail().equals(authService.getPrincipal().get().getEmail())) {
            List<User> users = userService.findPerformers(newPerformers);
            for (User user : users) {
                task.getPerformers().add(user);
            }
            taskRepository.save(task);
            return taskConverter.toDto(task);
        } else {
            throw new InsufficientRightsException("Вы не можете добавлять исполнителей к задачам, автором которых вы не являетесь.");
        }
    }

    @Override
    public TaskDto deletePerformer(int taskId, int performerId) throws InsufficientRightsException {
        Task task = taskRepository.findById(taskId);
        if (task.getAuthor().getEmail().equals(authService.getPrincipal().get().getEmail())) {
            task.getPerformers().removeIf(p -> p.getId() == performerId);
            taskRepository.save(task);
            return taskConverter.toDto(task);
        } else {
            throw new InsufficientRightsException("Вы не можете добавлять исполнителей к задачам, автором которых вы не являетесь.");
        }
    }

    @Override
    public TaskDto update(UpdateTaskDto taskDto, int id) throws InsufficientRightsException {
        Task task = taskRepository.findById(id);
        if (task.getAuthor().getEmail().equals(authService.getPrincipal().get().getEmail())) {
            if (taskDto.getTitle() != null && !taskDto.getTitle().isBlank()) {
                task.setTitle(taskDto.getTitle());
            }
            if (taskDto.getDescription() != null && !taskDto.getDescription().isBlank()) {
                task.setDescription(taskDto.getDescription());
            }
            if (taskDto.getStatus() != null && !taskDto.getStatus().isBlank()) {
                task.setStatus(taskDto.getStatus());
            }
            if (taskDto.getPriority() != null && !taskDto.getPriority().isBlank()) {
                task.setPriority(taskDto.getPriority());
            }
            taskRepository.save(task);
            return taskConverter.toDto(task);
        } else {
            throw new InsufficientRightsException("Вы не можете добавлять исполнителей к задачам, автором которых вы не являетесь.");
        }
    }

    @Override
    public List<TaskDto> advancedSearch(SearchParamsDto searchParamsDto, int pageNumber, int pageSize) {
        TaskSearchSpecification specification = new TaskSearchSpecification(searchParamsDto);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("priority").ascending());
        List<Task> tasks = taskRepository.findAll(specification, pageable).getContent();
        return tasks.stream().map(taskConverter::toDto).toList();
    }
}
