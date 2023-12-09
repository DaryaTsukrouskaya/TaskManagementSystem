package ru.effectivemobile.taskmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class UpdateTaskDto {
    private String title;
    private String description;
    private String status;
    private String priority;
}
