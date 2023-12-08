package ru.effectivemobile.taskmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;
import ru.effectivemobile.taskmanagementsystem.services.TaskService;


@RestController
@RequestMapping("/task")
@Tag(name = "task", description = "Task endpoints")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @Operation(
            summary = "Create task",
            description = "Create new task in content management system",
            tags = {"task"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Task was created",
                    content = @Content(schema = @Schema(contentSchema = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Task not created - server error"
            )
    })
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskDto taskDto, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.CREATED);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }


}
