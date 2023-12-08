package ru.effectivemobile.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class CommentDto {
    private int id;
    @NotEmpty(message = "текст комментария не должен быть пустым")
    @Size(max = 500, message = "длина комментария не должна превышать 500 символов")
    private String commentText;
    private LocalDate creationDate;
    @NotNull(message = "отсутствует id пользователя, написавшего комментарий")
    private int authorId;
    @NotNull(message = "отсутствует id задания, к которому относится комментарий")
    private int taskId;
}
