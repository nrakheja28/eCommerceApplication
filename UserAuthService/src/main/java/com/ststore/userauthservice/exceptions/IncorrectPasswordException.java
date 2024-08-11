package com.ststore.userauthservice.exceptions;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(){
        super("Incorrect Password");
    }
}
