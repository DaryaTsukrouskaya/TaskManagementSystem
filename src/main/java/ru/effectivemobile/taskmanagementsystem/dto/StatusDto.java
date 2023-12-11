package ru.effectivemobile.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class StatusDto {
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректный статус")
    @NotBlank(message = "статус задачи не должен быть пустым")
    private String status;
}
