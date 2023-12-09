package ru.effectivemobile.taskmanagementsystem.services;

import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.StatusDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.UpdateTaskDto;
import ru.effectivemobile.taskmanagementsystem.exceptions.InsufficientRightsException;

import java.util.List;

public interface TaskService {
    TaskDto createTask(CreateTaskDto taskDto);

    void deleteTask(int id) throws InsufficientRightsException;

    List<TaskDto> assignedTasks(int id, int pageNumber, int pageSize);

    List<TaskDto> performedTasks(int id, int pageNumber, int pageSize);

    TaskDto changeStatus(int taskId, StatusDto newStatus) throws InsufficientRightsException;

    TaskDto addPerformers(int taskId, List<String> newPerformers) throws InsufficientRightsException;

    TaskDto deletePerformer(int taskId, int performerId) throws InsufficientRightsException;

    TaskDto update(UpdateTaskDto taskDto, int id) throws InsufficientRightsException;
}
