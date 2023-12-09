package ru.effectivemobile.taskmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.PerformersDto;
import ru.effectivemobile.taskmanagementsystem.dto.SearchParamsDto;
import ru.effectivemobile.taskmanagementsystem.dto.StatusDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.UpdateTaskDto;
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
    public void deleteTask(@PathVariable @Min(value = 0, message = "id задачи не может быть отрицательным") Integer id) throws InsufficientRightsException {
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
    public ResponseEntity<List<TaskDto>> getUserAssignedTasks(@RequestParam(name = "page", defaultValue = "0") int pageNumber, @RequestParam(name = "size", defaultValue = "5") int pageSize) {
        return new ResponseEntity<>(taskService.assignedTasks(authService.getPrincipal().get().getId(), pageNumber, pageSize), HttpStatus.OK);
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
    @GetMapping("/performedBy/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskDto>> getUserPerformedTasks(@RequestParam(name = "page", defaultValue = "0") int pageNumber, @RequestParam(name = "size", defaultValue = "5") int pageSize) {
        return new ResponseEntity<>(taskService.performedTasks(authService.getPrincipal().get().getId(), pageNumber, pageSize), HttpStatus.OK);
    }

    @Operation(
            summary = "Find assigned tasks by certain user",
            description = "Find all assigned tasks by certain user id in the content management system",
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
    @GetMapping("/assignedBy/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskDto>> getAssignedTasksByCertainUser(@PathVariable @Min(value = 0, message = "id задачи не может быть отрицательным") Integer id, @RequestParam(name = "page", defaultValue = "0") int pageNumber, @RequestParam(name = "size", defaultValue = "5") int pageSize, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(taskService.assignedTasks(id, pageNumber, pageSize), HttpStatus.OK);
        } else {
            throw new ValidationException(result.getFieldError().getDefaultMessage());
        }

    }

    @Operation(
            summary = "Find performed tasks by certain user",
            description = "Find all performed tasks by certain user id in the content management system",
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
    @GetMapping("/performedBy/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskDto>> getPerformedTasksByCertainUser(@PathVariable @Min(value = 0, message =
            "id задачи не может быть отрицательным") Integer id, @RequestParam(name = "page", defaultValue = "0") int pageNumber, @RequestParam(name = "size", defaultValue = "5") int pageSize, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(taskService.performedTasks(id, pageNumber, pageSize), HttpStatus.OK);
        } else {
            throw new ValidationException(result.getFieldError().getDefaultMessage());
        }

    }

    @Operation(
            summary = "Change task status",
            description = "Change task status by task id in the content management system",
            tags = {"task"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task status was changed",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Tasks status was not changed"
            )
    })
    @PostMapping("/changeStatus/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDto> changeTaskStatus(@PathVariable @Min(value = 0, message = "id задачи не может быть отрицательным") int id, @RequestBody StatusDto status, BindingResult bindingResult) throws InsufficientRightsException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(taskService.changeStatus(id, status), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Add performers to the task",
            description = "Add performers to the task in the content management system",
            tags = {"task"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task performers was changed",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Tasks performers was not changed"
            )
    })
    @PostMapping("/addPerformers/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDto> addPerformers(@PathVariable @Min(value = 0, message = "id задачи не может быть отрицательным") @Min(0) int id, @RequestBody PerformersDto performers, BindingResult bindingResult) throws InsufficientRightsException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(taskService.addPerformers(id, performers.getPerformers()), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Delete performer from the task",
            description = "Delete performer from the task in the content management system",
            tags = {"task"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task performer was deleted",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Tasks performer was not deleted"
            )
    })
    @PostMapping("/deletePerformer/{taskId}/{performerId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDto> deletePerformer(@PathVariable @Min(value = 0, message = "id задачи не может быть отрицательным") int taskId,
                                                   @PathVariable @Min(value = 0, message = "id исполнителя не может быть отрицательным") int performerId,
                                                   BindingResult bindingResult) throws InsufficientRightsException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(taskService.deletePerformer(taskId, performerId), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Update task",
            description = "Update task in the content management system",
            tags = {"task"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task was updated",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Task was not updated - server error"
            )
    })
    @PostMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDto> update(@RequestBody @Valid UpdateTaskDto updateTaskDto, @PathVariable int id, BindingResult bindingResult) throws ValidationException, InsufficientRightsException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(taskService.update(updateTaskDto, id), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Find tasks by parameters",
            description = "Find tasks by specific parameters like price and category",
            tags = {"task"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products was found",
                    content = @Content(schema = @Schema(contentSchema = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Products not found - server error"
            )
    })
    @PostMapping("/advancedSearch")
    public ResponseEntity<List<TaskDto>> advancedSearch(@Valid @RequestBody SearchParamsDto searchParamsDto, @RequestParam(name = "page", defaultValue = "0") int pageNumber, @RequestParam(name = "size", defaultValue = "2") int pageSize, BindingResult result) throws ValidationException {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(taskService.advancedSearch(searchParamsDto, pageNumber, pageSize), HttpStatus.OK);
        } else {
            throw new ValidationException(result.getFieldError().getDefaultMessage());
        }
    }


}
