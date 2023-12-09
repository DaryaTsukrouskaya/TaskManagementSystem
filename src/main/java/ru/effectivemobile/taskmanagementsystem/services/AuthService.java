package ru.effectivemobile.taskmanagementsystem.services;


import lombok.NonNull;
import ru.effectivemobile.taskmanagementsystem.dto.JwtRequestDto;
import ru.effectivemobile.taskmanagementsystem.dto.JwtResponseDto;
import ru.effectivemobile.taskmanagementsystem.dto.UserDto;
import ru.effectivemobile.taskmanagementsystem.exceptions.AuthorizationException;
import ru.effectivemobile.taskmanagementsystem.exceptions.UserAlreadyExistsException;

import java.util.Optional;

public interface AuthService {
    JwtResponseDto login(@NonNull JwtRequestDto jwtRequestDto) throws AuthorizationException;

    JwtResponseDto getAccessToken(@NonNull String refreshToken);

    JwtResponseDto refresh(@NonNull String refreshToken) throws AuthorizationException;

    Optional<UserDto> getPrincipal();

    void register(UserDto userDto) throws UserAlreadyExistsException;
}
