package com.proyecto.MyCoach.service;


import com.proyecto.MyCoach.domain.Booking;
import com.proyecto.MyCoach.exception.BookingNotFoundException;

import java.util.List;

public interface BookingService {

    List<Booking> findAllBookings();

    Booking findById(Long id) throws BookingNotFoundException;

    Booking modifyBooking(Booking booking, Long id) throws BookingNotFoundException;

    Booking addBooking (Booking booking);

    Booking deleteBooking(Long id) throws BookingNotFoundException;

}
