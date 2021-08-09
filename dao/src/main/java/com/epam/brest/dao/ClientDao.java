package com.epam.brest.dao;

import com.epam.brest.model.Beverage;

import java.util.List;

public interface ClientDao {

    /**
     * When beverages is made,this method decreases ingredient's quantity
     */
    Integer updateIngredientQuantity(List<Beverage> selectedBeverages);
}
