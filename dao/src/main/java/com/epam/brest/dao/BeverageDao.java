package com.epam.brest.dao;

import com.epam.brest.model.Beverage;

import java.util.List;
import java.util.Optional;

public interface BeverageDao {

    /**
     * Return list with all beverages
     */
    List<Beverage> findAllBeverages();

    /**
     * Return list with all visible beverages for Client
     */
    List<Beverage> findVisibleBeverages();

    /**
     * Return selected beverage by ID
     */
    Optional<Beverage> findBeverageById(Integer beverageId);

    /**
     * Create new beverage
     */
    Integer createBeverage(Beverage beverage);

    /**
     * Update selected beverage
     */
    Integer updateBeverage(Beverage beverage);

    /**
     * Delete selected beverage
     */
    Integer deleteBeverage(Integer beverageId);
}
