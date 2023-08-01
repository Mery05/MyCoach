package com.proyecto.MyCoach.controller;

import com.proyecto.MyCoach.domain.User;
import com.proyecto.MyCoach.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping ("/users")
    public List<User> getUsers(){
        List<User> users;
        users = userService.findAllUsers();

        return users;
    }

    @GetMapping ("/user/{id}")
    public User getUserById (@PathVariable long id){
        User user = userService.findById(id);
        return user;
    }

    @PostMapping ("/user")
    public User addUser (@RequestBody User user){
        User newUser = userService.addUser(user);
        return newUser;
    }

    @DeleteMapping ("/user/{id}")
    public User deleteUser (@PathVariable long id){
        User user = userService.deleteUser(id);
        return user;
    }

    @PutMapping ("user/{id}")
    public User modifyUser (@RequestBody User user, @PathVariable long id){
        User newUser = userService.modifyUser(user, id);
        return newUser;
    }
}
