package com.epam.brest.service;

import com.epam.brest.model.Beverage;

import java.util.List;

public interface ClientService {

    /**
     * When beverages is made,this method decreases ingredient's quantity
     */
    Integer updateIngredientQuantity(List<Beverage> selectedBeverages);
}
