package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
    User findById(Long id);

    User addUser(User user);
    User modifyUser(User user, Long id);
    User deleteUser(Long id);
}
