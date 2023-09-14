package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Headquarter;
import com.proyecto.MyCoach.domain.Phisiotherapist;
import com.proyecto.MyCoach.exception.HeadquarterNotFoundException;

import java.util.List;

public interface HeadquarterService {

    List<Headquarter> findAllHeadquarters();

    Headquarter findById(Long id) throws HeadquarterNotFoundException;


    Headquarter addHeadquarter(Headquarter headquarter);

    Headquarter modifyHeadquarter(Headquarter headquarter, Long id) throws HeadquarterNotFoundException;

    Headquarter deleteHeadquarter(Long id) throws HeadquarterNotFoundException;
}


