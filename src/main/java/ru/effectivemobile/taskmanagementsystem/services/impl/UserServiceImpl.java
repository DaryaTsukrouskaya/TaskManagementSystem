package ru.effectivemobile.taskmanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.effectivemobile.taskmanagementsystem.entities.User;
import ru.effectivemobile.taskmanagementsystem.repositories.UserRepository;
import ru.effectivemobile.taskmanagementsystem.services.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<User> findPerformers(List<String> performers) {
        return userRepository.findAllByEmailIn(performers);
    }
}
