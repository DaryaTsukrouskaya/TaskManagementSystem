package ru.effectivemobile.taskmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class PerformersDto {
    private List<String> performers;
}
