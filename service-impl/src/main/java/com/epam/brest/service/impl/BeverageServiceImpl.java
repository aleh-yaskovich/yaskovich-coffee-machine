package com.epam.brest.service.impl;

import com.epam.brest.dao.BeverageDao;
import com.epam.brest.dao.IngredientDao;
import com.epam.brest.model.Beverage;
import com.epam.brest.model.Ingredient;
import com.epam.brest.service.BeverageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Transactional
public class BeverageServiceImpl implements BeverageService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BeverageServiceImpl.class);

    private final BeverageDao beverageDao;
    private final IngredientDao ingredientDao;

    public BeverageServiceImpl(BeverageDao beverageDao, IngredientDao ingredientDao) {
        this.beverageDao = beverageDao;
        this.ingredientDao = ingredientDao;
    }

    /**
     * Return list with all beverages
     */
    @Override
    public List<Beverage> findAllBeverages() {
        LOGGER.debug("findAllBeverages()");
        List<Beverage> beverages = beverageDao.findAllBeverages();
        List<Ingredient> ingredients = ingredientDao.findAllIngredients();
        for(Beverage beverage : beverages) {
            int quantity = calculateQuantity(beverage, ingredients);
            beverage.setBeverageQuantity(quantity);
        }
        return beverages;
    }

    /**
     * Return list with all visible beverages for Client
     */
    @Override
    public List<Beverage> findVisibleBeverages() {
        LOGGER.debug("findVisibleBeverages()");
        List<Beverage> beverages =  beverageDao.findVisibleBeverages();
        List<Ingredient> ingredients = ingredientDao.findAllIngredients();
        for(Beverage beverage : beverages) {
            int quantity = calculateQuantity(beverage, ingredients);
            beverage.setBeverageQuantity(quantity);
        }
        return beverages;
    }

    /**
     * Return selected beverage by ID
     */
    @Override
    public Beverage findBeverageById(Integer beverageId) throws IllegalArgumentException {
        LOGGER.debug("findBeverageById({})", beverageId);
        if(beverageDao.findBeverageById(beverageId).isPresent()) {
            Beverage beverage = beverageDao.findBeverageById(beverageId).get();
            List<Ingredient> ingredients = ingredientDao.findAllIngredients();
            int quantity = calculateQuantity(beverage, ingredients);
            beverage.setBeverageQuantity(quantity);
            return beverage;
        } else {
            throw new IllegalArgumentException("Beverage with ID "+beverageId+" is not found");
        }
    }

    /**
     * Create new beverage
     */
    @Override
    public Integer createBeverage(Beverage beverage) {
        LOGGER.debug("createBeverage({})", beverage);
        return beverageDao.createBeverage(beverage);
    }

    /**
     * Update selected beverage
     */
    @Override
    public Integer updateBeverage(Beverage beverage) {
        LOGGER.debug("updateBeverage({})", beverage);
        return beverageDao.updateBeverage(beverage);
    }

    /**
     * Delete selected beverage
     */
    @Override
    public Integer deleteBeverage(Integer beverageId) {
        LOGGER.debug("deleteBeverage({})", beverageId);
        return beverageDao.deleteBeverage(beverageId);
    }

    /**
     * Calculate, how many beverages the coffee-machine can make
     */
    private int calculateQuantity(Beverage beverage, List<Ingredient> ingredients) {
        SortedSet<Integer> quantity = new TreeSet<>();
        for (Ingredient ingredient : ingredients) {
            if(ingredient.getIngredientTitle().equals("Coffee")) {
                if(beverage.getBeverageIngCoffee() > 0)
                    quantity.add(ingredient.getIngredientQuantity() / beverage.getBeverageIngCoffee());
            }
            if(ingredient.getIngredientTitle().equals("Milk")) {
                if(beverage.getBeverageIngMilk() > 0)
                    quantity.add(ingredient.getIngredientQuantity() / beverage.getBeverageIngMilk());
            }
            if(ingredient.getIngredientTitle().equals("Chocolate")) {
                if(beverage.getBeverageIngChocolate() > 0)
                    quantity.add(ingredient.getIngredientQuantity() / beverage.getBeverageIngChocolate());
            }
            if(ingredient.getIngredientTitle().equals("Water")) {
                if(beverage.getBeverageIngWater() > 0)
                    quantity.add(ingredient.getIngredientQuantity() / beverage.getBeverageIngWater());
            }
        }
        if(!quantity.isEmpty()) {
            for (Ingredient ingredient : ingredients) {
                if(ingredient.getIngredientTitle().equals("Cup") || ingredient.getIngredientTitle().equals("Teaspoon"))
                    quantity.add(ingredient.getIngredientQuantity());
            }
            return quantity.first();
        }
        else return 0;
    }
}