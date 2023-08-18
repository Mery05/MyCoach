package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Trainer;
import com.proyecto.MyCoach.exception.TrainerNotFoundException;
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
    public Trainer findById(Long id) throws TrainerNotFoundException {
        return trainerRepository.findById(id)
                .orElseThrow(TrainerNotFoundException::new);
    }

    @Override
    public Trainer addTrainer(Trainer trainer) {

        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer modifyTrainer(Trainer newTrainer, Long id) throws TrainerNotFoundException{
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(TrainerNotFoundException::new);
        trainer.setName(newTrainer.getName());
        trainer.setSurname(newTrainer.getSurname());
        trainer.setHiringDate(newTrainer.getHiringDate());
        trainer.setAvailable(newTrainer.isAvailable());
        trainer.setPrice(newTrainer.getPrice());
        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer deleteTrainer(Long id) throws TrainerNotFoundException{
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(TrainerNotFoundException::new);
        trainerRepository.delete(trainer);
        return trainer;
    }
}
