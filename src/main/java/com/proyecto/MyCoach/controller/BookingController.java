package com.proyecto.MyCoach.controller;

import com.proyecto.MyCoach.domain.Booking;
import com.proyecto.MyCoach.domain.Phisiotherapist;
import com.proyecto.MyCoach.domain.Trainer;
import com.proyecto.MyCoach.domain.User;
import com.proyecto.MyCoach.exception.*;
import com.proyecto.MyCoach.repository.PhisiotherapistRepository;
import com.proyecto.MyCoach.repository.TrainerRepository;
import com.proyecto.MyCoach.repository.UserRepository;
import com.proyecto.MyCoach.service.BookingService;
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
    private UserRepository userRepository;
    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private PhisiotherapistRepository phisiotherapistRepository;

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings(){
        List<Booking> bookings;
        bookings = bookingService.findAllBookings();

        return ResponseEntity.ok(bookings);
    }

    @GetMapping ("/booking/{id}")
    public ResponseEntity <Booking> getBookingById (@PathVariable long id) throws BookingNotFoundException {
        Booking booking = bookingService.findById(id);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/user/{userId}/trainer/{trainerId}/booking")
    public ResponseEntity<Booking> addBookingTrainer (@Validated @RequestBody Booking booking, @PathVariable long userId, @PathVariable long trainerId) throws UserNotFoundException, TrainerNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(TrainerNotFoundException::new);

        Booking newBooking = bookingService.addBookingTrainer(booking, user, trainer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }

    @PostMapping("/user/{userId}/phisiotherapist/{phisiotherapistId}/booking")
    public ResponseEntity<Booking> addBookingPhisio (@Validated @RequestBody Booking booking, @PathVariable long userId, @PathVariable long phisiotherapistId) throws UserNotFoundException, PhisiotherapistNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Phisiotherapist phisiotherapist = phisiotherapistRepository.findById(phisiotherapistId)
                .orElseThrow(PhisiotherapistNotFoundException::new);

        Booking newBooking = bookingService.addBookingPhisio(booking, user, phisiotherapist);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }

    @DeleteMapping("/booking/{id}")
    public ResponseEntity<Booking> deleteBooking (@PathVariable long id)throws BookingNotFoundException{
        Booking booking = bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("booking/{id}")
    public ResponseEntity<Booking> modifyBooking (@RequestBody Booking booking, @PathVariable long id) throws BookingNotFoundException{
        Booking newBooking = bookingService.modifyBooking(booking, id);
        return ResponseEntity.status(HttpStatus.OK).body(newBooking);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ErrorMessage> bookingNotFoundException (BookingNotFoundException bnfe){
        ErrorMessage errorMessage = new ErrorMessage(404, bnfe.getMessage());
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
