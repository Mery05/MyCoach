package com.proyecto.MyCoach.controller;

import com.proyecto.MyCoach.domain.Booking;
import com.proyecto.MyCoach.domain.Headquarter;
import com.proyecto.MyCoach.exception.BookingNotFoundException;
import com.proyecto.MyCoach.exception.ErrorMessage;
import com.proyecto.MyCoach.exception.HeadquarterNotFoundException;
import com.proyecto.MyCoach.service.HeadquarterService;
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
public class HeadquarterController {

    @Autowired
    private HeadquarterService headquarterService;
    private final Logger logger = LoggerFactory.getLogger(HeadquarterController.class);

    @GetMapping("/headquarters")
    public ResponseEntity<List<Headquarter>> getHeadquarters(){
        List<Headquarter> headquarters;
        headquarters = headquarterService.findAllHeadquarters();
        logger.info("Listar todos los centros");
        return ResponseEntity.ok(headquarters);
    }

    @GetMapping ("/headquarter/{id}")
    public ResponseEntity <Headquarter> getHeadquarterById (@PathVariable long id) throws HeadquarterNotFoundException {
        Headquarter headquarter = headquarterService.findById(id);
        logger.info("buscar un centro por id");
        return ResponseEntity.ok(headquarter);
    }

    @PostMapping("/headquarter")
    public ResponseEntity<Headquarter> addHeadquarter (@Validated @RequestBody Headquarter headquarter){
        Headquarter newHeadquarter = headquarterService.addHeadquarter(headquarter);
        logger.info("añadir un centro");
        return ResponseEntity.status(HttpStatus.CREATED).body(newHeadquarter);
    }

    @DeleteMapping("/headquarter/{id}")
    public ResponseEntity<Headquarter> deleteHeadquarter (@PathVariable long id)throws HeadquarterNotFoundException{
        Headquarter headquarter = headquarterService.deleteHeadquarter(id);
        logger.info("borrar un centro");
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("headquarter/{id}")
    public ResponseEntity<Headquarter> modifyHeadquarter (@RequestBody Headquarter headquarter, @PathVariable long id) throws HeadquarterNotFoundException{
        Headquarter newHeadquarter = headquarterService.modifyHeadquarter(headquarter, id);
        logger.info("modificar un centro");
        return ResponseEntity.status(HttpStatus.OK).body(newHeadquarter);
    }

    @ExceptionHandler(HeadquarterNotFoundException.class)
    public ResponseEntity<ErrorMessage> headquarterNotFoundException (HeadquarterNotFoundException hnfe){
        logger.error(hnfe.getMessage(), hnfe);
        ErrorMessage errorMessage = new ErrorMessage(404, hnfe.getMessage());
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
