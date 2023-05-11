package com.elastic.example.util;

public class ConnectionNotCreatedException extends RuntimeException{

    private static final long serialVersionUID = -2538744186694200405L;

    public ConnectionNotCreatedException(String message) {
        super(message);
    }
}
