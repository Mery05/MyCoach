package com.proyecto.MyCoach.controller;

import com.proyecto.MyCoach.domain.Headquarter;
import com.proyecto.MyCoach.exception.ErrorMessage;
import com.proyecto.MyCoach.exception.HeadquarterNotFoundException;
import com.proyecto.MyCoach.service.HeadquarterService;
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

    @GetMapping("/headquarters")
    public ResponseEntity<List<Headquarter>> getHeadquarters(){
        List<Headquarter> headquarters;
        headquarters = headquarterService.findAllHeadquarters();

        return ResponseEntity.ok(headquarters);
    }

    @GetMapping ("/headquarter/{id}")
    public ResponseEntity <Headquarter> getHeadquarterById (@PathVariable long id) throws HeadquarterNotFoundException {
        Headquarter headquarter = headquarterService.findById(id);
        return ResponseEntity.ok(headquarter);
    }

    @PostMapping("/headquarter")
    public ResponseEntity<Headquarter> addHeadquarter (@Validated @RequestBody Headquarter headquarter){
        Headquarter newHeadquarter = headquarterService.addHeadquarter(headquarter);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHeadquarter);
    }

    @DeleteMapping("/headquarter/{id}")
    public ResponseEntity<Headquarter> deleteHeadquarter (@PathVariable long id)throws HeadquarterNotFoundException{
        Headquarter headquarter = headquarterService.deleteHeadquarter(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("headquarter/{id}")
    public ResponseEntity<Headquarter> modifyHeadquarter (@RequestBody Headquarter headquarter, @PathVariable long id) throws HeadquarterNotFoundException{
        Headquarter newHeadquarter = headquarterService.modifyHeadquarter(headquarter, id);
        return ResponseEntity.status(HttpStatus.OK).body(newHeadquarter);
    }

    @ExceptionHandler(HeadquarterNotFoundException.class)
    public ResponseEntity<ErrorMessage> headquarterNotFoundException (HeadquarterNotFoundException hnfe){
        ErrorMessage errorMessage = new ErrorMessage(404, hnfe.getMessage());
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
