package com.proyecto.MyCoach.exception;

public class TrainerNotFoundException extends Exception{

    public TrainerNotFoundException(){
        super ("Trainer not found");

    }

    public TrainerNotFoundException(String message){

        super(message);
    }

}
