package com.epam.brest.dao;

import com.epam.brest.model.Beverage;
import com.epam.brest.model.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientDao {

    /**
     * Return list with all ingredients
     */
    List<Ingredient> findAllIngredients();

    /**
     * Return selected ingredient by ID
     */
    Optional<Ingredient> findIngredientById(Integer ingredientId);

    /**
     * Update selected ingredient
     */
    Integer updateIngredient(Ingredient ingredient);
}
