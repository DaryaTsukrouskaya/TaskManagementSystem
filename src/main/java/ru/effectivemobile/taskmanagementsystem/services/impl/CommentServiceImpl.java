package ru.effectivemobile.taskmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.effectivemobile.taskmanagementsystem.dto.CommentDto;
import ru.effectivemobile.taskmanagementsystem.dto.CreateCommentDto;
import ru.effectivemobile.taskmanagementsystem.dto.converters.CommentConverter;
import ru.effectivemobile.taskmanagementsystem.repositories.CommentRepository;
import ru.effectivemobile.taskmanagementsystem.services.AuthService;
import ru.effectivemobile.taskmanagementsystem.services.CommentService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final AuthService authService;
    private final CommentConverter commentConverter;
    private final CommentRepository commentRepository;

    @Override
    public CommentDto createComment(CreateCommentDto createCommentDto, int taskId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(createCommentDto.getCommentText());
        commentDto.setCreationDate(LocalDate.now());
        commentDto.setTaskId(taskId);
        commentDto.setAuthorId(authService.getPrincipal().get().getId());
        commentRepository.save(commentConverter.fromDto(commentDto));
        return commentDto;
    }

    @Override
    public List<CommentDto> getComments(int taskId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("creationDate").descending());
        return commentRepository.findByTaskId(taskId, pageable).stream().map(commentConverter::toDto).toList();
    }


}
