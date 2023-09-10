package com.proyecto.MyCoach.controller;

import com.proyecto.MyCoach.domain.User;
import com.proyecto.MyCoach.exception.ErrorMessage;
import com.proyecto.MyCoach.exception.UserNotFoundException;
import com.proyecto.MyCoach.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @GetMapping ("/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users;
        users = userService.findAllUsers();
        logger.info("listar a todos los usuarios");
        return ResponseEntity.ok(users);
    }

    @GetMapping ("/user/{id}")
    public ResponseEntity <User> getUserById (@PathVariable long id) throws UserNotFoundException{
        User user = userService.findById(id);
        logger.info("buscar un usuario por id");
        return ResponseEntity.ok(user);
    }

    @PostMapping ("/user")
    public ResponseEntity<User> addUser (@Validated @RequestBody User user){
        User newUser = userService.addUser(user);
        logger.info("registrar un usuario");
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping ("/user/{id}")
    public ResponseEntity<User> deleteUser (@PathVariable long id)throws UserNotFoundException{
        User user = userService.deleteUser(id);
        logger.info("borrar un usuario");
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("user/{id}")
    public ResponseEntity<User> modifyUser (@RequestBody User user, @PathVariable long id) throws UserNotFoundException{
        User newUser = userService.modifyUser(user, id);
        logger.info("Modificar un usuario");
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException (UserNotFoundException unfe){
        logger.error(unfe.getMessage(), unfe);
        ErrorMessage errorMessage = new ErrorMessage(404, unfe.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e){
        logger.error(e.getMessage(), e);
        ErrorMessage errorMessage = new ErrorMessage(500,"Internal server Error");
        return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(MethodArgumentNotValidException manve){
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        ErrorMessage badRequestErrorMessage = new ErrorMessage(400, "Bad Request", errors);
        return new ResponseEntity<>(badRequestErrorMessage, HttpStatus.BAD_REQUEST);
    }
}
