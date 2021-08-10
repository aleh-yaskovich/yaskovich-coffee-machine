package com.epam.brest.service.impl;

import com.epam.brest.dao.BeverageDao;
import com.epam.brest.dao.IngredientDao;
import com.epam.brest.model.Beverage;
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

    public BeverageServiceImpl(BeverageDao beverageDao) {
        this.beverageDao = beverageDao;
    }

    /**
     * Return list with all beverages
     */
    @Override
    public List<Beverage> findAllBeverages() {
        LOGGER.debug("findAllBeverages()");
        return beverageDao.findAllBeverages();
    }

    /**
     * Return list with all visible beverages for Client
     */
    @Override
    public List<Beverage> findVisibleBeverages() {
        LOGGER.debug("findVisibleBeverages()");
        return beverageDao.findVisibleBeverages();
    }

    /**
     * Return selected beverage by ID
     */
    @Override
    public Beverage findBeverageById(Integer beverageId) throws IllegalArgumentException {
        LOGGER.debug("findBeverageById({})", beverageId);
        if(beverageDao.findBeverageById(beverageId).isPresent()) {
            return beverageDao.findBeverageById(beverageId).get();
        } else {
            throw new IllegalArgumentException("Beverage with ID "+beverageId+" is not found");
        }
    }

    /**
     * Create new beverage
     */
    @Override
    public Integer createBeverage(Beverage beverage) throws IllegalArgumentException {
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
}