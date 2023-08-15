package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.User;
import com.proyecto.MyCoach.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
    User findById(Long id) throws UserNotFoundException;

    User addUser(User user);
    User modifyUser(User user, Long id) throws UserNotFoundException;
    User deleteUser(Long id) throws UserNotFoundException;
}
