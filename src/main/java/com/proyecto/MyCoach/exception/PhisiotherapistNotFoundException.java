package com.proyecto.MyCoach.exception;

public class PhisiotherapistNotFoundException extends Exception{

    public PhisiotherapistNotFoundException(){
        super ("Phisiotherapist not found");

    }

    public PhisiotherapistNotFoundException(String message){

        super(message);
    }
}
