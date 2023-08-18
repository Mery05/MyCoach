package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Phisiotherapist;
import com.proyecto.MyCoach.exception.PhisiotherapistNotFoundException;

import java.util.List;

public interface PhisiotherapistService {

    List<Phisiotherapist> findAllPhisiotherapist();

    Phisiotherapist findById(Long id) throws PhisiotherapistNotFoundException;

    Phisiotherapist addPhisiotherapist(Phisiotherapist phisiotherapist);

    Phisiotherapist modifyPhisiotherapist(Phisiotherapist phisiotherapist, Long id) throws PhisiotherapistNotFoundException;

    Phisiotherapist deletePhisiotherapist(Long id) throws PhisiotherapistNotFoundException;
}
