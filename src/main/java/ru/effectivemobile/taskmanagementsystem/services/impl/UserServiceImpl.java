package ru.effectivemobile.taskmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.effectivemobile.taskmanagementsystem.dto.converters.UserConverter;
import ru.effectivemobile.taskmanagementsystem.entities.User;
import ru.effectivemobile.taskmanagementsystem.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public List<User> getPerformers(List<String> performers) {
        return userRepository.findAllByEmailIn(performers);
    }
}
