package ru.effectivemobile.taskmanagementsystem.services;

import ru.effectivemobile.taskmanagementsystem.entities.User;

import java.util.List;

public interface UserService {
    List<User> findPerformers(List<String> performers);
}
