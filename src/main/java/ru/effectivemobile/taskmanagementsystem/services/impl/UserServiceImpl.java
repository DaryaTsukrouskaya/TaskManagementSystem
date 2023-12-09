package ru.effectivemobile.taskmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.effectivemobile.taskmanagementsystem.dto.UserDto;
import ru.effectivemobile.taskmanagementsystem.dto.converters.UserConverter;
import ru.effectivemobile.taskmanagementsystem.entities.User;
import ru.effectivemobile.taskmanagementsystem.repositories.UserRepository;
import ru.effectivemobile.taskmanagementsystem.services.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public List<User> findPerformers(List<String> performers) {
        return userRepository.findAllByEmailIn(performers);
    }

    public UserDto findUser(int id) {
        User user = userRepository.findById(id);
        return userConverter.toDto(user);
    }
}
