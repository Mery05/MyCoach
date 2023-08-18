package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Booking;
import com.proyecto.MyCoach.exception.BookingNotFoundException;
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
    public Booking findById(Long id) throws BookingNotFoundException {
        return bookingRepository.findById(id)
                .orElseThrow(BookingNotFoundException::new);
    }

    @Override
    public Booking modifyBooking(Booking newBooking, Long id) throws BookingNotFoundException{
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(BookingNotFoundException::new);
        booking.setBookingDate(newBooking.getBookingDate());
        booking.setStartTime(newBooking.getStartTime());
        booking.setDuration(newBooking.getDuration());
        booking.setTools(newBooking.isTools());
        booking.setPaid(newBooking.isPaid());

        return bookingRepository.save(booking);
    }

    @Override
    public Booking addBooking(Booking booking) {
        return bookingRepository.save(booking);

    }

    @Override
    public Booking deleteBooking(Long id) throws BookingNotFoundException{
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(BookingNotFoundException::new);
        bookingRepository.delete(booking);
        return booking;
    }
}
