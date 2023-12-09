package ru.effectivemobile.taskmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;
import ru.effectivemobile.taskmanagementsystem.exceptions.InsufficientRightsException;
import ru.effectivemobile.taskmanagementsystem.services.AuthService;
import ru.effectivemobile.taskmanagementsystem.services.TaskService;

import java.util.List;


@RestController
@RequestMapping("/task")
@Tag(name = "task", description = "Task endpoints")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final AuthService authService;

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

    @Operation(
            summary = "Delete task",
            description = "Delete existed task by task author",
            tags = {"task"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task was deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Task was not deleted - server error"
            )
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteTask(@PathVariable @Min(0) Integer id) throws InsufficientRightsException {
        taskService.deleteTask(id);
    }

    @Operation(
            summary = "Find user assigned tasks",
            description = "Find all tasks assigned by user",
            tags = {"task"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Assigned tasks was found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Assigned tasks was not found - server error"
            )
    })
    @GetMapping("/assigned")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskDto>> getUserAssignedTasks() {
        return new ResponseEntity<>(taskService.userAssignedTasks(authService.getPrincipal().get().getId()), HttpStatus.OK);
    }

    @Operation(
            summary = "Find user performed tasks",
            description = "Find all user performed tasks",
            tags = {"task"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Performed tasks was found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Performed tasks was not found - server error"
            )
    })
    @GetMapping("/performed")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskDto>> getUserPerformedTasks() {
        return new ResponseEntity<>(taskService.userAssignedTasks(authService.getPrincipal().get().getId()), HttpStatus.OK);
    }


}
