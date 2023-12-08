package ru.effectivemobile.taskmanagementsystem.services;

import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;

public interface TaskService {
    TaskDto createTask(CreateTaskDto taskDto);
}
