package com.example.grocery.Exception;

import java.io.Serial;

public class IllegalArgumentException extends Exception{

    @Serial
    private static final long serialVersionUID = 2921933468684518632L;

    public IllegalArgumentException(String string) {
        super(string);
    }
}
