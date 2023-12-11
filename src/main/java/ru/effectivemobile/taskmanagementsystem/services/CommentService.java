package ru.effectivemobile.taskmanagementsystem.services;

import ru.effectivemobile.taskmanagementsystem.dto.CommentDto;
import ru.effectivemobile.taskmanagementsystem.dto.CreateCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CreateCommentDto createCommentDto, int taskId);

    List<CommentDto> getComments(int taskId, int pageNumber, int pageSize);
}
