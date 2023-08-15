package com.proyecto.MyCoach.exception;

public class UserNotFoundException extends Exception{

        public UserNotFoundException(){
            super ("User not found");

        }

        public UserNotFoundException(String message){

            super(message);
        }
}
