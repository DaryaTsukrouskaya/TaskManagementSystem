package ru.effectivemobile.taskmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SearchParamsDto {
    private String title;
    private String description;
    private String status;
    private String priority;
    private int authorId;
    private int performerId;

}
