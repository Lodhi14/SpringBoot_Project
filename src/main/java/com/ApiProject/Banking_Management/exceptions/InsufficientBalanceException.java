package com.ApiProject.Banking_Management.exceptions;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(){
        super();
    }

    public InsufficientBalanceException(String message)
    {
        super(message);
    }
}
