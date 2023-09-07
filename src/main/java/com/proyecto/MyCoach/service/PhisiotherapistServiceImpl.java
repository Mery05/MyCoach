package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Headquarter;
import com.proyecto.MyCoach.domain.Phisiotherapist;
import com.proyecto.MyCoach.exception.HeadquarterNotFoundException;
import com.proyecto.MyCoach.exception.PhisiotherapistNotFoundException;
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
    public Phisiotherapist findById(Long id) throws PhisiotherapistNotFoundException {
        return phisiotherapistRepository.findById(id)
                .orElseThrow(PhisiotherapistNotFoundException::new);
    }

    @Override
    public Phisiotherapist addPhisiotherapist(Phisiotherapist phisiotherapist, Headquarter headquarter) throws PhisiotherapistNotFoundException, HeadquarterNotFoundException {
        phisiotherapist.setHeadquarter(headquarter);
        return phisiotherapistRepository.save(phisiotherapist);
    }

    @Override
    public Phisiotherapist modifyPhisiotherapist(Phisiotherapist newPhisiotherapist, Long id) throws PhisiotherapistNotFoundException{
        Phisiotherapist phisiotherapist = phisiotherapistRepository.findById(id)
                .orElseThrow(PhisiotherapistNotFoundException::new);
        phisiotherapist.setName(newPhisiotherapist.getName());
        phisiotherapist.setSurname(newPhisiotherapist.getSurname());
        phisiotherapist.setHiringDate(newPhisiotherapist.getHiringDate());
        phisiotherapist.setAvailable(newPhisiotherapist.isAvailable());
        phisiotherapist.setPrice(newPhisiotherapist.getPrice());
        phisiotherapist.setHeadquarter(newPhisiotherapist.getHeadquarter());

        return phisiotherapistRepository.save(phisiotherapist);
    }

    @Override
    public Phisiotherapist deletePhisiotherapist(Long id) throws PhisiotherapistNotFoundException{
        Phisiotherapist phisiotherapist = phisiotherapistRepository.findById(id)
                .orElseThrow(PhisiotherapistNotFoundException::new);
        phisiotherapistRepository.delete(phisiotherapist);
        return phisiotherapist;
    }
}
