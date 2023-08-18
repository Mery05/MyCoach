package com.proyecto.MyCoach.exception;

import com.proyecto.MyCoach.domain.Booking;

public class BookingNotFoundException extends Exception{

    public BookingNotFoundException(){
        super ("Booking not found");

    }

    public BookingNotFoundException(String message){

        super(message);
    }
}
