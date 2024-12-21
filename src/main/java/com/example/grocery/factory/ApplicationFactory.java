package com.example.grocery.factory;

import com.example.grocery.Exception.ExceptionResponse;
import com.example.grocery.Generic.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

public class ApplicationFactory {
    private ApplicationFactory() {
        super();
    }

    public static ApplicationFactory getInstance() {
        return ApplicationFactoryWrapper.INSTANCE;
    }

    public Response<Object> createResponse(String message, HttpStatus status) {
        return new Response<>(message, status.name(), status.value());
    }

    public Response<Object> createResponse(String message, HttpStatus status, Object data) {
        return new Response<>(message, status.name(), status.value(), data);
    }

    public ExceptionResponse createExceptionResponse(String errorMessage, HttpStatus status) {
        return new ExceptionResponse(errorMessage, status.name(), status.value());
    }

    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    private static class ApplicationFactoryWrapper {
        public static final ApplicationFactory INSTANCE = new ApplicationFactory();
    }
}
