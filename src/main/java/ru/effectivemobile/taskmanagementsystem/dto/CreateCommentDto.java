package ru.effectivemobile.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class CreateCommentDto {
    @NotEmpty(message = "текст комментария не должен быть пустым")
    @Size(max = 500, message = "длина комментария не должна превышать 500 символов")
    private String commentText;
}
