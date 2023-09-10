package com.proyecto.MyCoach.repository;

import com.proyecto.MyCoach.domain.Booking;
import com.proyecto.MyCoach.domain.Headquarter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeadquarterRepositoy extends CrudRepository<Headquarter, Long> {

    List<Headquarter> findAll();

    List<Headquarter> findByBookingId(int bookingId);
}
