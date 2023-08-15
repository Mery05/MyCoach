package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Booking;
import com.proyecto.MyCoach.domain.Trainer;
import com.proyecto.MyCoach.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;


    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(null);
    }

    @Override
    public Booking modifyBooking(Long id, Booking newBooking) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(null);
        booking.setBookingDate(newBooking.getBookingDate());
        booking.setStartTime(newBooking.getStartTime());

        return bookingRepository.save(booking);
    }

    @Override
    public Booking addBooking(Booking booking) {
        return bookingRepository.save(booking);

    }

    @Override
    public Booking deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(null);
        bookingRepository.delete(booking);
        return booking;
    }
}
