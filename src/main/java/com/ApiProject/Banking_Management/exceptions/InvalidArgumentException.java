package com.ApiProject.Banking_Management.exceptions;

public class InvalidArgumentException extends RuntimeException{
    public InvalidArgumentException(){
        super();
    }

    public InvalidArgumentException(String message){
        super(message);
    }
}
