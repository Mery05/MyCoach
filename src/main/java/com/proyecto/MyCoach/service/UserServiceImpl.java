package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.User;
import com.proyecto.MyCoach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(null);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User modifyUser(User newUser, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(null);
        user.setName(newUser.getName());
        user.setSurname(newUser.getSurname());
        user.setBirthDate(newUser.getBirthDate());
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(null);

        userRepository.delete(user);
        return user;
    }
}
