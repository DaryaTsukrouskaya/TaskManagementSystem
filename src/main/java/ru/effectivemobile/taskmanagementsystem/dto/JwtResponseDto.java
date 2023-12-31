package ru.effectivemobile.taskmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class JwtResponseDto {
    private final String type = "Bearer";
    private String accessToke;
    private String refreshToken;
}
