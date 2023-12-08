package ru.effectivemobile.taskmanagementsystem.dto;

import jakarta.validation.constraints.Email;
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
public class UserDto {
    private int id;
    @NotBlank(message = "имя не должно быть пустым")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректное имя")
    private String name;
    @NotBlank(message = "фамилия не должна быть пустой")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректная фамилия")
    private String surname;
    @NotNull(message = "заполните поле дата рождения")
    @Past(message = "указанная дата рождения еще не наступила")
    private LocalDate birthDate;
    @Email(message = "некорректный email")
    @NotBlank(message = "email не должен быть пустым")
    private String email;
    @Size(min = 6, max = 10, message = "длина пароля должна быть от 6 до 10 символов")
    @Pattern(regexp = "\\S+",
            message = "пароль не должен содержать пробелы")
    private String password;

    private List<TaskDto> assignedTasks;
    private List<TaskDto> performedTasks;
}
