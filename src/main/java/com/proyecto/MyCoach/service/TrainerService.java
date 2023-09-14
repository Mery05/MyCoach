package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Headquarter;
import com.proyecto.MyCoach.domain.Trainer;
import com.proyecto.MyCoach.exception.HeadquarterNotFoundException;
import com.proyecto.MyCoach.exception.TrainerNotFoundException;

import java.util.List;

public interface TrainerService {

    List<Trainer> findAllTrainers();

    Trainer findById(Long id) throws TrainerNotFoundException;
    Trainer addTrainer(Trainer trainer, Headquarter headquarter) throws TrainerNotFoundException, HeadquarterNotFoundException;

    Trainer modifyTrainer(Trainer trainer, Long id) throws TrainerNotFoundException;

    Trainer deleteTrainer(Long id) throws TrainerNotFoundException;

}


