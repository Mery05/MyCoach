package com.proyecto.MyCoach.service;

import com.proyecto.MyCoach.domain.Headquarter;
import com.proyecto.MyCoach.exception.HeadquarterNotFoundException;
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
    public Headquarter findById(Long id) throws HeadquarterNotFoundException {
        return headquarterRepositoy.findById(id)
                .orElseThrow(HeadquarterNotFoundException::new);
    }

    @Override
    public Headquarter addHeadquarter(Headquarter headquarter) {

        return headquarterRepositoy.save(headquarter);
    }

    @Override
    public Headquarter modifyHeadquarter(Headquarter newHeadquarter, Long id) throws HeadquarterNotFoundException{
        Headquarter headquarter = headquarterRepositoy.findById(id)
                .orElseThrow(HeadquarterNotFoundException::new);
        headquarter.setName(newHeadquarter.getName());
        headquarter.setCity(newHeadquarter.getCity());
        headquarter.setAddress(newHeadquarter.getAddress());
        headquarter.setEnrollmentNumber(newHeadquarter.getEnrollmentNumber());
        headquarter.setClassNumber(newHeadquarter.getClassNumber());


        return headquarterRepositoy.save(headquarter);
    }

    @Override
    public Headquarter deleteHeadquarter(Long id) throws HeadquarterNotFoundException{
        Headquarter headquarter = headquarterRepositoy.findById(id)
                .orElseThrow(HeadquarterNotFoundException::new);
        headquarterRepositoy.delete(headquarter);
        return headquarter;
    }
}
