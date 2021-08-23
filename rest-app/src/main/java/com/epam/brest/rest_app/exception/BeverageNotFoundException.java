package com.epam.brest.rest_app.exception;

public class BeverageNotFoundException extends RuntimeException{
    BeverageNotFoundException(Integer id) {
        super("Could not find beverage " + id);
    }
}
