package com.proyecto.MyCoach.controller;

import com.proyecto.MyCoach.domain.Booking;
import com.proyecto.MyCoach.exception.BookingNotFoundException;
import com.proyecto.MyCoach.exception.ErrorMessage;
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

    @PostMapping("/booking")
    public ResponseEntity<Booking> addBooking (@Validated @RequestBody Booking booking){
        Booking newBooking = bookingService.addBooking(booking);
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
