package com.ApiProject.Banking_Management.exceptions;

public class ForeignKeyConstraintException extends RuntimeException{
    public ForeignKeyConstraintException(){
        super();
    }
    public ForeignKeyConstraintException(String message)
    {
        super(message);
    }
}
