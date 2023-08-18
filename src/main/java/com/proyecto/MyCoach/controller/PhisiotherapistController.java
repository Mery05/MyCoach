package com.proyecto.MyCoach.controller;

import com.proyecto.MyCoach.domain.Phisiotherapist;
import com.proyecto.MyCoach.exception.ErrorMessage;
import com.proyecto.MyCoach.exception.PhisiotherapistNotFoundException;
import com.proyecto.MyCoach.service.PhisiotherapistService;
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
public class PhisiotherapistController {

    @Autowired
    private PhisiotherapistService phisiotherapistService;

    @GetMapping("/phisiotherapists")
    public ResponseEntity<List<Phisiotherapist>> getPhisiotherapist(){
        List<Phisiotherapist> phisiotherapists;
        phisiotherapists = phisiotherapistService.findAllPhisiotherapist();

        return ResponseEntity.ok(phisiotherapists);
    }

    @GetMapping ("/phisiotherapist/{id}")
    public ResponseEntity <Phisiotherapist> getPhisiotherapistById (@PathVariable long id) throws PhisiotherapistNotFoundException {
        Phisiotherapist phisiotherapist = phisiotherapistService.findById(id);
        return ResponseEntity.ok(phisiotherapist);
    }

    @PostMapping("/phisiotherapist")
    public ResponseEntity<Phisiotherapist> addPhisiotherapist (@Validated @RequestBody Phisiotherapist phisiotherapist){
        Phisiotherapist newPhisiotherapis = phisiotherapistService.addPhisiotherapist(phisiotherapist);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPhisiotherapis);
    }

    @DeleteMapping ("/phisiotherapist/{id}")
    public ResponseEntity<Phisiotherapist> deletePhisiotherapist (@PathVariable long id)throws PhisiotherapistNotFoundException{
        Phisiotherapist phisiotherapist = phisiotherapistService.deletePhisiotherapist(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("phisiotherapist/{id}")
    public ResponseEntity<Phisiotherapist> modifyPhisiotherapist (@RequestBody Phisiotherapist phisiotherapist, @PathVariable long id) throws PhisiotherapistNotFoundException{
        Phisiotherapist newPhisiotherapist = phisiotherapistService.modifyPhisiotherapist(phisiotherapist, id);
        return ResponseEntity.status(HttpStatus.OK).body(newPhisiotherapist);
    }

    @ExceptionHandler(PhisiotherapistNotFoundException.class)
    public ResponseEntity<ErrorMessage> phisiotherapistNotFoundException (PhisiotherapistNotFoundException pnfe){
        ErrorMessage errorMessage = new ErrorMessage(404, pnfe.getMessage());
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
