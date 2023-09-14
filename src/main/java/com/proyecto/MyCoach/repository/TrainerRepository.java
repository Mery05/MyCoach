package com.proyecto.MyCoach.repository;

import com.proyecto.MyCoach.domain.Trainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    List<Trainer> findAll();

}
