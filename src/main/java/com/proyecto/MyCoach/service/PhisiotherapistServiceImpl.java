package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Phisiotherapist;
import com.proyecto.MyCoach.repository.PhisiotherapistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhisiotherapistServiceImpl implements PhisiotherapistService{

    @Autowired
    PhisiotherapistRepository phisiotherapistRepository;

    @Override
    public List<Phisiotherapist> findAllPhisiotherapist() {
        return phisiotherapistRepository.findAll();
    }

    @Override
    public Phisiotherapist findById(Long id) {
        return phisiotherapistRepository.findById(id)
                .orElseThrow(null);
    }

    @Override
    public Phisiotherapist addPhisiotherapist(Phisiotherapist phisiotherapist) {
        return phisiotherapistRepository.save(phisiotherapist);
    }

    @Override
    public Phisiotherapist modifyPhisiotherapist(Phisiotherapist newPhisiotherapist, Long id) {
        Phisiotherapist phisiotherapist = phisiotherapistRepository.findById(id)
                .orElseThrow(null);
        phisiotherapist.setName(newPhisiotherapist.getName());
        phisiotherapist.setAvailable(newPhisiotherapist.isAvailable());

        return phisiotherapistRepository.save(phisiotherapist);
    }

    @Override
    public Phisiotherapist deletePhisiotherapist(Long id) {
        Phisiotherapist phisiotherapist = phisiotherapistRepository.findById(id)
                .orElseThrow(null);
        phisiotherapistRepository.delete(phisiotherapist);
        return phisiotherapist;
    }
}
