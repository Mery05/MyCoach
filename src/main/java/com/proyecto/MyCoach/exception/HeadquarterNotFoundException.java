package com.proyecto.MyCoach.exception;

import com.proyecto.MyCoach.domain.Headquarter;

public class HeadquarterNotFoundException extends Exception{

    public HeadquarterNotFoundException(){
        super ("Headquarter not found");

    }

    public HeadquarterNotFoundException(String message){

        super(message);
    }
}
