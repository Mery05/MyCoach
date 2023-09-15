

package com.proyecto.MyCoach.controller;

import com.proyecto.MyCoach.domain.*;
import com.proyecto.MyCoach.exception.*;
import com.proyecto.MyCoach.repository.PhisiotherapistRepository;
import com.proyecto.MyCoach.repository.TrainerRepository;
import com.proyecto.MyCoach.repository.UserRepository;
import com.proyecto.MyCoach.service.BookingService;
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
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhisiotherapistRepository phisiotherapistRepository;

    private final Logger logger = LoggerFactory.getLogger(BookingController.class);


    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings(){
        List<Booking> bookings;
        bookings = bookingService.findAllBookings();
        logger.info("Listar todos los centros");
        return ResponseEntity.ok(bookings);
    }
    @GetMapping("/booking/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable long id) throws BookingNotFoundException {
        Booking booking = bookingService.findById(id);
        logger.info("Buscar reservas por id");
        return ResponseEntity.ok(booking);
    }

/*
    //Operación de filtrado de reservas por entrenador
    @GetMapping("/booking")
    public List<Booking> getBookings(@RequestParam(name = "trainer-booking", required = false, defaultValue = "") String trainerId) throws BookingNotFoundException{
        List<Booking> bookings;

        logger.info("filtrar entrenadores por reserva");
        if (trainerId.equals("")){
            bookings = bookingService.findAllBookings();
            logger.info("En caso de que no existan reservas para ese entrenador se muestra un array vacio");
        } else{
            bookings = bookingService.findByTrainer(Integer.parseInt(trainerId));
            logger.info("muestra las reservas que tiene un entrenador en concreto");
        }
        return bookings;
    }

 */


    @PostMapping("/user/{userId}/trainer/{trainerId}/booking")
    public ResponseEntity<Booking> addBookingTrainer (@Validated @RequestBody Booking booking, @PathVariable long userId, @PathVariable long trainerId) throws UserNotFoundException, TrainerNotFoundException {
        logger.info("empieza a buscar un usuario");
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        logger.info("Usuario encontrado");
        logger.info("empieza a buscar un entrenador");
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(TrainerNotFoundException::new);
        logger.info("Buscando entrenador por id");
        Booking newBooking = bookingService.addBookingTrainer(booking, user, trainer);
        logger.info("Añadiendo una nueva reserva de entrenador");
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }

    @PostMapping("/user/{userId}/phisiotherapist/{phisiotherapistId}/booking")
    public ResponseEntity<Booking> addBookingPhisio (@Validated @RequestBody Booking booking, @PathVariable long userId, @PathVariable long phisiotherapistId) throws UserNotFoundException, PhisiotherapistNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        logger.info("Buscando usuario por id");
        Phisiotherapist phisiotherapist = phisiotherapistRepository.findById(phisiotherapistId)
                .orElseThrow(PhisiotherapistNotFoundException::new);
        logger.info("Buscando fisioterapeuta por id");

        Booking newBooking = bookingService.addBookingPhisio(booking, user, phisiotherapist);
        logger.info("Añadiendo una nueva reserva de fisioterapeuta");
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }


    @DeleteMapping("/booking/{id}")
    public ResponseEntity<Booking> deleteBooking (@PathVariable long id)throws BookingNotFoundException{
        Booking booking = bookingService.deleteBooking(id);
        logger.info("Borrar una reserva");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("booking/{id}")
    public ResponseEntity<Booking> modifyBooking (@RequestBody Booking booking, @PathVariable long id) throws BookingNotFoundException{
        Booking newBooking = bookingService.modifyBooking(booking, id);
        logger.info("modificar un reserva");
        return ResponseEntity.status(HttpStatus.OK).body(newBooking);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ErrorMessage> bookingNotFoundException (BookingNotFoundException bnfe){
        logger.error(bnfe.getMessage(), bnfe);
        ErrorMessage errorMessage = new ErrorMessage(404, bnfe.getMessage());
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


