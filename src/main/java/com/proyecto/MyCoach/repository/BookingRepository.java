package com.proyecto.MyCoach.repository;

import com.proyecto.MyCoach.domain.Booking;
import com.proyecto.MyCoach.domain.Phisiotherapist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List <Booking> findAll();

    //List<Booking> findByTrainerId(int trainerId);
}
