package ru.effectivemobile.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class TaskDto {
    private int id;

    @NotBlank(message = "заголовок задачи не должен быть пустым")
    @Size(max = 60, message = "длина заголовка не должна превышать 60 символов")
    private String title;

    @NotBlank(message = "описание задачи не должно быть пустым")
    @Size(max = 2000, message = "длина описания задачи не должна превышать 2000 символов")
    private String description;

    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректный статус")
    @NotBlank(message = "статус задачи не должен быть пустым")
    private String status;

    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректный приоритет")
    @NotBlank(message = "приоритет задачи не должен быть пустым")
    private String priority;
    @Past(message = "указанная дата  еще не наступила")
    @NotNull(message = "заполните поле дата задания")
    private LocalDate creationDate;
    @NotNull(message = "заполните поле автор задания")
    private int authorId;
    private List<UserDto> performers;
    private List<CommentDto> comments;
}
