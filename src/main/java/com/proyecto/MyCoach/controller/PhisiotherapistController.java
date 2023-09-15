package com.proyecto.MyCoach.controller;


import com.proyecto.MyCoach.domain.Headquarter;
import com.proyecto.MyCoach.domain.Phisiotherapist;
import com.proyecto.MyCoach.exception.ErrorMessage;
import com.proyecto.MyCoach.exception.HeadquarterNotFoundException;
import com.proyecto.MyCoach.exception.PhisiotherapistNotFoundException;
import com.proyecto.MyCoach.service.HeadquarterService;
import com.proyecto.MyCoach.service.PhisiotherapistService;
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
public class PhisiotherapistController {

    @Autowired
    private PhisiotherapistService phisiotherapistService;

    @Autowired
    private HeadquarterService headquarterService;


    private final Logger logger = LoggerFactory.getLogger(PhisiotherapistController.class);


    @GetMapping("/phisiotherapists")
    public ResponseEntity<List<Phisiotherapist>> getPhisiotherapist(){
        List<Phisiotherapist> phisiotherapists;
        phisiotherapists = phisiotherapistService.findAllPhisiotherapist();
        logger.info("listar todos los fisios");
        return ResponseEntity.ok(phisiotherapists);
    }

    @GetMapping ("/phisiotherapist/{id}")
    public ResponseEntity <Phisiotherapist> getPhisiotherapistById (@PathVariable long id) throws PhisiotherapistNotFoundException {
        Phisiotherapist phisiotherapist = phisiotherapistService.findById(id);
        logger.info("buscar un fisio por id");
        return ResponseEntity.ok(phisiotherapist);
    }

    //Operación de filtrado de fisios por centro
    @GetMapping("/phisiotherapist")
    public List<Phisiotherapist> getPhisiotherapist(@RequestParam(name = "headquarter-phisiotherapist", required = false, defaultValue = "") String headquarterId) throws PhisiotherapistNotFoundException{
        List<Phisiotherapist> phisiotherapists;

        logger.info("filtrar fisios por centro");
        if (headquarterId.equals("")){
            phisiotherapists = phisiotherapistService.findAllPhisiotherapist();
            logger.info("En caso de que no existan fisios en el centro se muestra un array vacio");
        } else{
            phisiotherapists = phisiotherapistService.findByHeadquarter(Integer.parseInt(headquarterId));
            logger.info("muestra los fisios que hay en el centro buscado");
        }
        return phisiotherapists;
    }





    @PostMapping("/headquarter/{headquarterId}/phisiotherapist")
    public ResponseEntity<Phisiotherapist> addPhisiotherapist (@Validated @RequestBody Phisiotherapist phisiotherapist, @PathVariable long headquarterId) throws PhisiotherapistNotFoundException, HeadquarterNotFoundException {
        Headquarter headquarter = headquarterService.findById(headquarterId);
        logger.info("buscar un centro por id");
        Phisiotherapist newPhisiotherapis = phisiotherapistService.addPhisiotherapist(phisiotherapist, headquarter);
        logger.info("añadir un fisio a un centro");
        return ResponseEntity.status(HttpStatus.CREATED).body(newPhisiotherapis);
    }




    @DeleteMapping ("/phisiotherapist/{id}")
    public ResponseEntity<Phisiotherapist> deletePhisiotherapist (@PathVariable long id)throws PhisiotherapistNotFoundException{
        Phisiotherapist phisiotherapist = phisiotherapistService.deletePhisiotherapist(id);
        logger.info("borrar un centro");
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("phisiotherapist/{id}")
    public ResponseEntity<Phisiotherapist> modifyPhisiotherapist (@RequestBody Phisiotherapist phisiotherapist, @PathVariable long id) throws PhisiotherapistNotFoundException{
        Phisiotherapist newPhisiotherapist = phisiotherapistService.modifyPhisiotherapist(phisiotherapist, id);
        logger.info("modificar un centro");
        return ResponseEntity.status(HttpStatus.OK).body(newPhisiotherapist);
    }

    @ExceptionHandler(PhisiotherapistNotFoundException.class)
    public ResponseEntity<ErrorMessage> phisiotherapistNotFoundException (PhisiotherapistNotFoundException pnfe){
        logger.error(pnfe.getMessage(), pnfe);
        ErrorMessage errorMessage = new ErrorMessage(404, pnfe.getMessage());
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


