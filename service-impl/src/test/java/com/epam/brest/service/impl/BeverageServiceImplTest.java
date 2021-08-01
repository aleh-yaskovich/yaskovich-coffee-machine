package com.epam.brest.service.impl;

import com.epam.brest.dao.jdbc.BeverageDaoJdbc;
import com.epam.brest.dao.jdbc.IngredientDaoJdbc;
import com.epam.brest.model.Beverage;
import com.epam.brest.model.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BeverageServiceImplTest {

    @InjectMocks
    private BeverageServiceImpl beverageService;

    @Mock
    private IngredientDaoJdbc ingredientDao;

    @Mock
    private BeverageDaoJdbc beverageDao;

    private List<Beverage> beverageList = Arrays.asList(
            new Beverage(1,"Espresso",30,0,0,0,true,false,true,0.99,0,true),
            new Beverage(2,"Americano",60,0,0,90,true,false,true,1.98,0,true),
            new Beverage(3,"Latte",30,100,0,0,true,true,true,1.76,0,true)
    );

    private List<Ingredient> ingredientList = Arrays.asList(
            new Ingredient(1,"Coffee",2500,Date.valueOf("2021-12-31"),31.50,true),
            new Ingredient(2,"Milk",1000,Date.valueOf("2021-12-31"),2.50,true),
            new Ingredient(3,"Chocolate",1000,Date.valueOf("2021-12-31"),8.35,true),
            new Ingredient(4,"Water",5000,Date.valueOf("2021-12-31"),0.63,true),
            new Ingredient(5,"Sugar",1000,Date.valueOf("2021-12-31"),1.68,false),
            new Ingredient(6,"Syrup",400,Date.valueOf("2021-12-31"),3.25,false),
            new Ingredient(7,"Cinnamon",100,Date.valueOf("2021-12-31"),5.24,false),
            new Ingredient(8,"Cup",250,Date.valueOf("2025-12-31"),19.00,true),
            new Ingredient(9,"Teaspoon",250,Date.valueOf("2025-12-31"),9.99,true)
    );

    @Test
    void findAllBeveragesTest() {
        Mockito.when(beverageDao.findAllBeverages()).thenReturn(beverageList);
        Mockito.when(ingredientDao.findAllIngredients()).thenReturn(ingredientList);
        List<Beverage> beverages = beverageService.findAllBeverages();
        assertNotNull(beverages);
        assertFalse(beverages.isEmpty());
        assertFalse(beverages.get(0).getBeverageQuantity() == 0);
    }

    @Test
    void findVisibleBeveragesTest() {
        Mockito.when(beverageDao.findVisibleBeverages()).thenReturn(beverageList);
        Mockito.when(ingredientDao.findAllIngredients()).thenReturn(ingredientList);
        List<Beverage> beverages = beverageService.findVisibleBeverages();
        assertNotNull(beverages);
        assertFalse(beverages.isEmpty());
        assertFalse(beverages.get(0).getBeverageQuantity() == 0);
    }

    @Test
    void findBeverageByIdTest() {
        beverageList.get(0).setBeverageQuantity(0);
        Mockito.when(ingredientDao.findAllIngredients()).thenReturn(ingredientList);
        Mockito.when(beverageDao.findBeverageById(1)).thenReturn(Optional.ofNullable(beverageList.get(0)));
        Beverage beverage = beverageService.findBeverageById(1);
        assertNotNull(beverage);
        assertEquals(beverage.getBeverageTitle(), beverageList.get(0).getBeverageTitle());
        assertFalse(beverageList.get(0).getBeverageQuantity() == 0);
    }

    @Test
    void findBeverageByIdTestWithException() {
        int beverageId = 99;
        Mockito.when(beverageDao.findBeverageById(any())).thenReturn(Optional.empty());
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{beverageService.findBeverageById(beverageId);} );
        assertEquals("Beverage with ID "+beverageId+" is not found", exception.getMessage());
    }

    @Test
    void createBeverageTest() {
        Mockito.when(beverageDao.createBeverage(any())).thenReturn(1);
        int result = beverageService.createBeverage(new Beverage());
        assertTrue(result == 1);

        Mockito.when(beverageDao.createBeverage(any())).thenReturn(0);
        result = beverageService.createBeverage(new Beverage());
        assertTrue(result == 0);
    }

    @Test
    void updateBeverageTest() {
        Mockito.when(beverageDao.updateBeverage(any())).thenReturn(1);
        int result = beverageService.updateBeverage(new Beverage());
        assertTrue(result == 1);

        Mockito.when(beverageDao.updateBeverage(any())).thenReturn(0);
        result = beverageService.updateBeverage(new Beverage());
        assertTrue(result == 0);
    }

    @Test
    void deleteBeverageTest() {
        Mockito.when(beverageDao.deleteBeverage(any())).thenReturn(1);
        int result = beverageService.deleteBeverage(1);
        assertTrue(result == 1);

        Mockito.when(beverageDao.deleteBeverage(any())).thenReturn(0);
        result = beverageService.deleteBeverage(1);
        assertTrue(result == 0);
    }
}