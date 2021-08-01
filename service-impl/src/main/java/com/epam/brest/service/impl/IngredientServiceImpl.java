package com.epam.brest.service.impl;

import com.epam.brest.dao.IngredientDao;
import com.epam.brest.model.Ingredient;
import com.epam.brest.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class IngredientServiceImpl implements IngredientService {

    private final static Logger LOGGER = LoggerFactory.getLogger(IngredientServiceImpl.class);

    private final IngredientDao ingredientDao;

    public IngredientServiceImpl(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    /**
     * Return list with all ingredients
     */
    @Override
    public List<Ingredient> findAllIngredients() {
        LOGGER.debug("findAllIngredients()");
        return ingredientDao.findAllIngredients();
    }

    /**
     * Return selected ingredient by ID
     */
    @Override
    public Ingredient findIngredientById(Integer ingredientId) throws IllegalArgumentException {
        LOGGER.debug("findIngredientById({})", ingredientId);
        if(ingredientDao.findIngredientById(ingredientId).isPresent()) {
            return ingredientDao.findIngredientById(ingredientId).get();
        } else {
            throw new IllegalArgumentException("Ingredient with ID " + ingredientId + " is not found");
        }
    }

    /**
     * Update selected ingredient
     */
    @Override
    public Integer updateIngredient(Ingredient ingredient) {
        LOGGER.debug("updateIngredient({})", ingredient);
        return ingredientDao.updateIngredient(ingredient);
    }
}