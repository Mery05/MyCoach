package com.proyecto.MyCoach.service;


import com.proyecto.MyCoach.domain.Booking;
import com.proyecto.MyCoach.domain.Phisiotherapist;
import com.proyecto.MyCoach.domain.Trainer;
import com.proyecto.MyCoach.domain.User;
import com.proyecto.MyCoach.exception.BookingNotFoundException;

import java.util.List;

public interface BookingService {

    List<Booking> findAllBookings();

    Booking findById(Long id) throws BookingNotFoundException;
    List<Booking> findByTrainer(int id);

    Booking modifyBooking(Booking booking, Long id) throws BookingNotFoundException;

    Booking addBookingTrainer (Booking booking, User user, Trainer trainer);

    Booking addBookingPhisio (Booking booking, User user, Phisiotherapist phisiotherapist);

    Booking deleteBooking(Long id) throws BookingNotFoundException;

}
