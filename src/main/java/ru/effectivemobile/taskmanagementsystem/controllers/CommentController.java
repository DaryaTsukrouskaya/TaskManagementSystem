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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.taskmanagementsystem.dto.CommentDto;
import ru.effectivemobile.taskmanagementsystem.dto.CreateCommentDto;
import ru.effectivemobile.taskmanagementsystem.services.CommentService;

import java.util.List;


@RestController
@RequestMapping("/comment")
@Tag(name = "comment", description = "Comment endpoints")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "Create comment",
            description = "Create comment for certain task by authenticated user",
            tags = {"comment"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Comment was created",
                    content = @Content(schema = @Schema(contentSchema = CommentDto.class))
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
            return new ResponseEntity<>(commentService.createComment(commentDto, id), HttpStatus.CREATED);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Find all task comments",
            description = "Find all comments for certain task in content management system",
            tags = {"comment"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comments were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Comments not fount - server error"
            )
    })
    @GetMapping("/getAll/{id}")
    public ResponseEntity<List<CommentDto>> getCategoryProducts(@PathVariable @Min(value = 0, message = "id задачи не может быть отрицательным") int id, @RequestParam(name = "page", defaultValue = "0") int pageNumber, @RequestParam(name = "size", defaultValue = "5") int pageSize, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(commentService.getComments(id, pageNumber, pageSize), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }
}
