package com.epam.brest.service.impl;

import com.epam.brest.dao.jdbc.BeverageDaoJdbc;
import com.epam.brest.model.Beverage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private BeverageDaoJdbc beverageDao;

    private List<Beverage> beverageList = Arrays.asList(
            new Beverage(1,"Espresso",30,0,0,0,true,false,true,0.99,0,true),
            new Beverage(2,"Americano",60,0,0,90,true,false,true,1.98,0,true),
            new Beverage(3,"Latte",30,100,0,0,true,true,true,1.76,0,true)
    );

    @Test
    void findAllBeveragesTest() {
        Mockito.when(beverageDao.findAllBeverages()).thenReturn(beverageList);
        List<Beverage> beverages = beverageService.findAllBeverages();
        assertNotNull(beverages);
        assertFalse(beverages.isEmpty());
    }

    @Test
    void findVisibleBeveragesTest() {
        Mockito.when(beverageDao.findVisibleBeverages()).thenReturn(beverageList);
        List<Beverage> beverages = beverageService.findVisibleBeverages();
        assertNotNull(beverages);
        assertFalse(beverages.isEmpty());
    }

    @Test
    void findBeverageByIdTest() {
        beverageList.get(0).setBeverageQuantity(0);
        Mockito.when(beverageDao.findBeverageById(1)).thenReturn(Optional.ofNullable(beverageList.get(0)));
        Beverage beverage = beverageService.findBeverageById(1);
        assertNotNull(beverage);
        assertEquals(beverage.getBeverageTitle(), beverageList.get(0).getBeverageTitle());
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