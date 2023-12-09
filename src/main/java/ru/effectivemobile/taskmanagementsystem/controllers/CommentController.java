package ru.effectivemobile.taskmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.taskmanagementsystem.dto.CommentDto;
import ru.effectivemobile.taskmanagementsystem.dto.CreateCommentDto;
import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;

@RestController
@RequestMapping("/comment")
@Tag(name = "comment", description = "Comment endpoints")
@RequiredArgsConstructor
public class CommentController {
    @Operation(
            summary = "Create comment",
            description = "Create comment for certain task by authenticated user",
            tags = {"comment"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Comment was created",
                    content = @Content(schema = @Schema(contentSchema = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Comment was not created - server error"
            )
    })
    @PostMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentDto> createComment(@RequestBody CreateCommentDto commentDto, @PathVariable @Min(value = 0, message = "id задачи не может быть отрицательным") int id, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.CREATED);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }
}
