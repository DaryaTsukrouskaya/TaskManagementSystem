package ru.effectivemobile.taskmanagementsystem.dto.converters;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.effectivemobile.taskmanagementsystem.dto.UserDto;
import ru.effectivemobile.taskmanagementsystem.entities.User;

import java.util.Optional;

@Component
public class UserConverter {
    private final TaskConverter taskConverter;

    public UserConverter(@Lazy TaskConverter taskConverter) {
        this.taskConverter = taskConverter;
    }

    public UserDto toDto(User user) {
        return Optional.ofNullable(user).map(u -> UserDto.builder().id(u.getId()).name(u.getName()).surname(u.getSurname())
                .birthDate(u.getBirthDate()).email(u.getEmail()).password(u.getPassword()).
                assignedTasks(Optional.ofNullable(u.getAssignedTasks()).map(t -> t.stream()
                        .map(taskConverter::toDto).toList()).orElse(null)).performedTasks(Optional.ofNullable
                        (u.getAssignedTasks()).map(t -> t.stream()
                        .map(taskConverter::toDto).toList()).orElse(null)).build()).orElse(null);
    }

    public User fromDto(UserDto user) {
        return Optional.ofNullable(user).map(u -> User.builder().id(u.getId()).name(u.getName()).surname(u.getSurname())
                .birthDate(u.getBirthDate()).email(u.getEmail()).password(u.getPassword()).
                assignedTasks(Optional.ofNullable(u.getAssignedTasks()).map(t -> t.stream()
                        .map(taskConverter::fromDto).toList()).orElse(null)).performedTasks(Optional.ofNullable
                        (u.getAssignedTasks()).map(t -> t.stream()
                        .map(taskConverter::fromDto).toList()).orElse(null)).build()).orElse(null);
    }
}
