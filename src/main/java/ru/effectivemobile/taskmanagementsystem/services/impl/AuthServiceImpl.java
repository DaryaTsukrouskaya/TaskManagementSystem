package ru.effectivemobile.taskmanagementsystem.services.impl;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.effectivemobile.taskmanagementsystem.config.JwtProvider;
import ru.effectivemobile.taskmanagementsystem.dto.JwtRequestDto;
import ru.effectivemobile.taskmanagementsystem.dto.JwtResponseDto;
import ru.effectivemobile.taskmanagementsystem.dto.UserDto;
import ru.effectivemobile.taskmanagementsystem.dto.converters.UserConverter;
import ru.effectivemobile.taskmanagementsystem.entities.Token;
import ru.effectivemobile.taskmanagementsystem.entities.User;
import ru.effectivemobile.taskmanagementsystem.exceptions.AuthorizationException;
import ru.effectivemobile.taskmanagementsystem.exceptions.UserAlreadyExistsException;
import ru.effectivemobile.taskmanagementsystem.repositories.TokenRepository;
import ru.effectivemobile.taskmanagementsystem.repositories.UserRepository;
import ru.effectivemobile.taskmanagementsystem.services.AuthService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    @Override
    public JwtResponseDto login(@NonNull JwtRequestDto jwtRequestDto) throws AuthorizationException {
        UserDto user = userRepository.findByEmail(jwtRequestDto.getLogin()).map(userConverter::toDto)
                .orElseThrow(() -> new AuthorizationException("User is not found"));
        if (passwordEncoder.matches(jwtRequestDto.getPassword(), user.getPassword())) {
            String accessToken = jwtProvider.GenerateAccessToken(jwtRequestDto.getLogin());
            String refreshToken = jwtProvider.GenerateRefreshToken(jwtRequestDto.getLogin());
            Token refreshTokenFromRepository = tokenRepository.findByUsername(jwtRequestDto.getLogin());
            if (refreshTokenFromRepository != null) {
                refreshTokenFromRepository.setToken(refreshToken);
                tokenRepository.save(refreshTokenFromRepository);
            } else {
                tokenRepository.save(Token.builder().id(0).token(refreshToken).username(user.getEmail()).build());
            }
            return new JwtResponseDto(accessToken, refreshToken);
        } else {
            throw new AuthorizationException("Неправильный пароль");
        }
    }

    @Override
    public JwtResponseDto getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            String repositoryToken = tokenRepository.findByUsername(login).getToken();
            if (repositoryToken != null && repositoryToken.equals(refreshToken)) {
                String accessToken = jwtProvider.GenerateAccessToken(login);
                return new JwtResponseDto(accessToken, null);
            }
        }
        return new JwtResponseDto(null, null);
    }

    @Override
    public JwtResponseDto refresh(@NonNull String refreshToken) throws AuthorizationException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            Token repositoryToken = tokenRepository.findByUsername(login);
            if (repositoryToken != null && repositoryToken.getToken().equals(refreshToken)) {
                String accessToken = jwtProvider.GenerateAccessToken(login);
                String newRefreshToken = jwtProvider.GenerateRefreshToken(login);
                repositoryToken.setToken(newRefreshToken);
                tokenRepository.save(repositoryToken);
                return new JwtResponseDto(accessToken, newRefreshToken);
            }
        }
        throw new AuthorizationException("Невалидный JWT токен");
    }

    @Override
    public Optional<UserDto> getPrincipal() {
        return userRepository.findByEmail(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().
                getPrincipal()).getUsername()).map(userConverter::toDto);
    }

    @Override
    public void register(UserDto userDto) throws UserAlreadyExistsException {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setBirthDate(userDto.getBirthDate());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Такой пользователь уже существует");
        }
        userRepository.save(user);
    }
}
