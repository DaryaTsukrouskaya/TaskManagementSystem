package ru.effectivemobile.taskmanagementsystem.services;

import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;
import ru.effectivemobile.taskmanagementsystem.exceptions.InsufficientRightsException;

import java.util.List;

public interface TaskService {
    TaskDto createTask(CreateTaskDto taskDto);
    void deleteTask(int id) throws InsufficientRightsException;
    List<TaskDto> userAssignedTasks(int id);
    List<TaskDto> userPerformedTasks(int id);
}
