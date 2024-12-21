package com.example.grocery.Exception;

import java.io.Serial;

public class InvalidInputException extends Exception{

    @Serial
    private static final long serialVersionUID = 2921933468684518631L;

    public InvalidInputException(String string) {
        super(string);
    }
}
