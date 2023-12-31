package ru.effectivemobile.taskmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.taskmanagementsystem.dto.JwtRequestDto;
import ru.effectivemobile.taskmanagementsystem.dto.JwtResponseDto;
import ru.effectivemobile.taskmanagementsystem.dto.RefreshJwtRequestDto;
import ru.effectivemobile.taskmanagementsystem.dto.UserDto;
import ru.effectivemobile.taskmanagementsystem.exceptions.AuthorizationException;
import ru.effectivemobile.taskmanagementsystem.exceptions.UserAlreadyExistsException;
import ru.effectivemobile.taskmanagementsystem.services.AuthService;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth", description = "Authentication and register endpoints")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Authenticate user",
            description = "User authentication  in eshop by email and password with returning access and refresh tokens",
            tags = {"auth"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User was authenticated",
                    content = @Content(schema = @Schema(implementation = JwtResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User was not authenticated - server error"
            )
    }
    )
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody JwtRequestDto jwtRequestDto) throws AuthorizationException {
        return new ResponseEntity<>(authService.login(jwtRequestDto), HttpStatus.OK);
    }

    @Operation(summary = "Access token",
            description = "Get new access token by refresh token",
            tags = {"auth"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Access token successfully received",
                    content = @Content(schema = @Schema(implementation = JwtResponseDto.class))
            )
    }
    )
    @PostMapping("/token")
    public ResponseEntity getNewAccessToken(@Valid @RequestBody RefreshJwtRequestDto refreshJwtRequestDto) {
        return new ResponseEntity<>(authService.getAccessToken(refreshJwtRequestDto.getRefreshToken()), HttpStatus.OK);
    }

    @Operation(summary = "Refresh token",
            description = "Get new refresh token",
            tags = {"auth"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Refresh token successfully received",
                    content = @Content(schema = @Schema(implementation = JwtResponseDto.class))
            )
    }
    )
    @PostMapping("/refresh")
    public ResponseEntity getNewRefreshToken(@Valid @RequestBody RefreshJwtRequestDto refreshJwtRequestDto) throws AuthorizationException {
        return new ResponseEntity<>(authService.refresh(refreshJwtRequestDto.getRefreshToken()), HttpStatus.OK);
    }

    @Operation(
            summary = "Register user",
            description = "Register new user",
            tags = {"auth"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User was created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User was not created - server error"
            )
    })
    @PostMapping("/register")
    public void create(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) throws UserAlreadyExistsException {
        authService.register(userDto);
    }
}
