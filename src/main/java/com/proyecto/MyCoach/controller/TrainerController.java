package com.proyecto.MyCoach.controller;

import com.proyecto.MyCoach.domain.Headquarter;
import com.proyecto.MyCoach.domain.Trainer;
import com.proyecto.MyCoach.exception.ErrorMessage;
import com.proyecto.MyCoach.exception.HeadquarterNotFoundException;
import com.proyecto.MyCoach.exception.TrainerNotFoundException;
import com.proyecto.MyCoach.service.HeadquarterService;
import com.proyecto.MyCoach.service.TrainerService;
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
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private HeadquarterService headquarterService;

    private final Logger logger = LoggerFactory.getLogger(TrainerController.class);

    @GetMapping("/trainers")
    public ResponseEntity<List<Trainer>> getTrainers(){
        List<Trainer> trainers;
        trainers = trainerService.findAllTrainers();
        logger.info("listar a todos los entrenadores");
        return ResponseEntity.ok(trainers);
    }

    @GetMapping ("/trainer/{id}")
    public ResponseEntity <Trainer> getTrainerById (@PathVariable long id) throws TrainerNotFoundException {
        Trainer trainer = trainerService.findById(id);
        logger.info("buscar un entrenador por id");
        return ResponseEntity.ok(trainer);
    }


    @PostMapping("/headquarter/{headquarterId}/trainer")
    public ResponseEntity<Trainer> addTrainer (@Validated @RequestBody Trainer trainer, @PathVariable long headquarterId) throws TrainerNotFoundException, HeadquarterNotFoundException {
        Headquarter headquarter = headquarterService.findById(headquarterId);
        logger.info("buscar un centro por id");
        Trainer newTrainer = trainerService.addTrainer(trainer, headquarter);
        logger.info("registrar un entrenador en un centro");
        return ResponseEntity.status(HttpStatus.CREATED).body(newTrainer);
    }



    @DeleteMapping ("/trainer/{id}")
    public ResponseEntity<Trainer> deleteTrainer (@PathVariable long id)throws TrainerNotFoundException{
        Trainer trainer = trainerService.deleteTrainer(id);
        logger.info("Eliminar un entrenador");
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("trainer/{id}")
    public ResponseEntity<Trainer> modifyTrainer (@RequestBody Trainer trainer, @PathVariable long id) throws TrainerNotFoundException{
        Trainer newTrainer = trainerService.modifyTrainer(trainer, id);
        logger.info("modificar un entrenador");
        return ResponseEntity.status(HttpStatus.OK).body(newTrainer);
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<ErrorMessage> trainerNotFoundException (TrainerNotFoundException tnfe){
        logger.error(tnfe.getMessage(), tnfe);
        ErrorMessage errorMessage = new ErrorMessage(404, tnfe.getMessage());
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


