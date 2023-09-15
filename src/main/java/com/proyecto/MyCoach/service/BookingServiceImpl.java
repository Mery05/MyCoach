package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Booking;
import com.proyecto.MyCoach.domain.Phisiotherapist;
import com.proyecto.MyCoach.domain.Trainer;
import com.proyecto.MyCoach.domain.User;
import com.proyecto.MyCoach.exception.BookingNotFoundException;
import com.proyecto.MyCoach.repository.BookingRepository;
import com.proyecto.MyCoach.repository.TrainerRepository;
import com.proyecto.MyCoach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainerRepository trainerRepository;



    @Override
    public List<Booking> findAllBookings() {

        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(Long id) throws BookingNotFoundException {
        return bookingRepository.findById(id)
                .orElseThrow(BookingNotFoundException::new);
    }

/*
    @Override
    public List<Booking> findByTrainer(int bookingId) {

        return bookingRepository.findByTrainerId(bookingId);
    }

 */




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
    public Booking addBookingTrainer(Booking booking, User user, Trainer trainer) {
        booking.setUser(user);
        booking.setTrainer(trainer);
        return bookingRepository.save(booking);

    }


    @Override
    public Booking addBookingPhisio(Booking booking, User user, Phisiotherapist phisiotherapist) {
        booking.setUser(user);
        booking.setPhisiotherapist(phisiotherapist);
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


