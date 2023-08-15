package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Trainer;

import java.util.List;

public interface TrainerService {

    List<Trainer> findAllTrainers();

    Trainer findById(Long id);

    Trainer addTrainer(Trainer trainer);

    Trainer modifyTrainer(Trainer trainer, Long id);

    Trainer deleteTrainer(Long id);

}
