package com.epam.brest.service;

import com.epam.brest.model.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {

    /**
     * Return list with all ingredients
     */
    List<Ingredient> findAllIngredients();

    /**
     * Return selected ingredient by ID
     */
    Ingredient findIngredientById(Integer ingredientId);

    /**
     * Update selected ingredient
     */
    Integer updateIngredient(Ingredient ingredient);
}
