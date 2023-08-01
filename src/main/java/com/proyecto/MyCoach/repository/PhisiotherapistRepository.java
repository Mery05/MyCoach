package com.proyecto.MyCoach.repository;

import com.proyecto.MyCoach.domain.Phisiotherapist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhisiotherapistRepository extends CrudRepository<Phisiotherapist, Long> {

    List<Phisiotherapist> findAll();
}
