package com.epam.brest.rest_app.exception;

public class IngredientNotFoundException extends RuntimeException {
    IngredientNotFoundException(Integer id) {
        super("Could not find ingrediente " + id);
    }
}
