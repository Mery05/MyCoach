package com.proyecto.MyCoach.controller;

import com.proyecto.MyCoach.domain.Trainer;
import com.proyecto.MyCoach.exception.ErrorMessage;
import com.proyecto.MyCoach.exception.TrainerNotFoundException;
import com.proyecto.MyCoach.service.TrainerService;
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
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/trainers")
    public ResponseEntity<List<Trainer>> getTrainers(){
        List<Trainer> trainers;
        trainers = trainerService.findAllTrainers();

        return ResponseEntity.ok(trainers);
    }

    @GetMapping ("/trainer/{id}")
    public ResponseEntity <Trainer> getTrainerById (@PathVariable long id) throws TrainerNotFoundException {
        Trainer trainer = trainerService.findById(id);
        return ResponseEntity.ok(trainer);
    }

    @PostMapping("/trainer")
    public ResponseEntity<Trainer> addTrainer (@Validated @RequestBody Trainer trainer){
        Trainer newTrainer = trainerService.addTrainer(trainer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTrainer);
    }

    @DeleteMapping ("/trainer/{id}")
    public ResponseEntity<Trainer> deleteTrainer (@PathVariable long id)throws TrainerNotFoundException{
        Trainer trainer = trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("trainer/{id}")
    public ResponseEntity<Trainer> modifyTrainer (@RequestBody Trainer trainer, @PathVariable long id) throws TrainerNotFoundException{
        Trainer newTrainer = trainerService.modifyTrainer(trainer, id);
        return ResponseEntity.status(HttpStatus.OK).body(newTrainer);
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<ErrorMessage> trainerNotFoundException (TrainerNotFoundException tnfe){
        ErrorMessage errorMessage = new ErrorMessage(404, tnfe.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
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
