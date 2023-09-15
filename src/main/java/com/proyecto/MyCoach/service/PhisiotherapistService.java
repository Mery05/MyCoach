package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Headquarter;
import com.proyecto.MyCoach.domain.Phisiotherapist;
import com.proyecto.MyCoach.exception.HeadquarterNotFoundException;
import com.proyecto.MyCoach.exception.PhisiotherapistNotFoundException;

import java.util.List;

public interface PhisiotherapistService {

    List<Phisiotherapist> findAllPhisiotherapist();
    List<Phisiotherapist> findByHeadquarter(int id);

    Phisiotherapist findById(Long id) throws PhisiotherapistNotFoundException;

    Phisiotherapist addPhisiotherapist(Phisiotherapist phisiotherapist, Headquarter headquarter) throws PhisiotherapistNotFoundException, HeadquarterNotFoundException;

    Phisiotherapist modifyPhisiotherapist(Phisiotherapist phisiotherapist, Long id) throws PhisiotherapistNotFoundException;

    Phisiotherapist deletePhisiotherapist(Long id) throws PhisiotherapistNotFoundException;
}


