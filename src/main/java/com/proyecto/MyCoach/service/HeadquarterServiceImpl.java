package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Headquarter;
import com.proyecto.MyCoach.repository.HeadquarterRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadquarterServiceImpl implements HeadquarterService{

    @Autowired
    private HeadquarterRepositoy headquarterRepositoy;

    @Override
    public List<Headquarter> findAllHeadquarters() {
        return headquarterRepositoy.findAll();
    }

    @Override
    public Headquarter findById(Long id) {
        return headquarterRepositoy.findById(id)
                .orElseThrow(null);
    }

    @Override
    public Headquarter addHeadquarter(Headquarter headquarter) {
        return headquarterRepositoy.save(headquarter);
    }

    @Override
    public Headquarter modifyHeadquarter(Headquarter newHeadquarter, Long id) {
        Headquarter headquarter = headquarterRepositoy.findById(id)
                .orElseThrow(null);
        headquarter.setCity(newHeadquarter.getCity());
        headquarter.setName(newHeadquarter.getName());

        return headquarterRepositoy.save(headquarter);
    }

    @Override
    public Headquarter deleteHeadquarter(Long id) {
        Headquarter headquarter = headquarterRepositoy.findById(id)
                .orElseThrow(null);
        headquarterRepositoy.delete(headquarter);
        return headquarter;
    }
}
