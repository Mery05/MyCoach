package com.proyecto.MyCoach.service;


import com.proyecto.MyCoach.domain.Booking;
import java.util.List;

public interface BookingService {

    List<Booking> findAllBookings();

    Booking findById(Long id);

    Booking modifyBooking(Long id, Booking booking);

    Booking addBooking (Booking booking);

    Booking deleteBooking(Long id);

}
