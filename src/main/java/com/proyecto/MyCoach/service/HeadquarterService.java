package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Headquarter;

import java.util.List;

public interface HeadquarterService {

    List<Headquarter> findAllHeadquarters();

    Headquarter findById(Long id);

    Headquarter addHeadquarter(Headquarter headquarter);

    Headquarter modifyHeadquarter(Headquarter headquarter, Long id);

    Headquarter deleteHeadquarter(Long id);
}
