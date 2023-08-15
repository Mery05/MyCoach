package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Phisiotherapist;

import java.util.List;

public interface PhisiotherapistService {

    List<Phisiotherapist> findAllPhisiotherapist();

    Phisiotherapist findById(Long id);

    Phisiotherapist addPhisiotherapist(Phisiotherapist phisiotherapist);

    Phisiotherapist modifyPhisiotherapist(Phisiotherapist phisiotherapist, Long id);

    Phisiotherapist deletePhisiotherapist(Long id);
}
