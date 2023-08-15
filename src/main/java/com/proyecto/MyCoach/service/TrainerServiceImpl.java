package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Trainer;
import com.proyecto.MyCoach.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService{

    @Autowired
    private TrainerRepository trainerRepository;

    @Override
    public List<Trainer> findAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer findById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(null);
    }

    @Override
    public Trainer addTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer modifyTrainer(Trainer newTrainer, Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(null);
        trainer.setName(newTrainer.getName());
        trainer.setAvailable(newTrainer.isAvailable());
        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(null);
        trainerRepository.delete(trainer);
        return trainer;
    }
}
