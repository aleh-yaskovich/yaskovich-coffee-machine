package com.epam.brest.dao.jdbc;

import com.epam.brest.model.Beverage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db-config.xml","classpath:test-dao.xml"})
class BeverageDaoJdbcIT {

    @Autowired
    private BeverageDaoJdbc beverageDaoJdbc;

    @Test
    void findAllBeveragesTestNotNull() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        assertNotNull(beverages);
    }

    @Test
    void findAllBeveragesTestNotEmpty() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        assertFalse(beverages.isEmpty());
        assertTrue(beverages.get(0).getBeveragePrice() > 0);
        assertTrue(beverages.get(0).getBeverageQuantity() > 0);
    }

    @Test
    void findVisibleBeveragesTest() {
        List<Beverage> beverages = beverageDaoJdbc.findVisibleBeverages();
        assertFalse(beverages.isEmpty());
        Beverage beverage = beverages.get(0);
        beverage.setBeverageHidden(false);
        beverageDaoJdbc.updateBeverage(beverage);
        List<Beverage> testBeverages = beverageDaoJdbc.findVisibleBeverages();
        assertTrue((beverages.size()-1) == testBeverages.size());
        assertTrue(beverages.get(0).getBeveragePrice() > 0);
        assertTrue(beverages.get(0).getBeverageQuantity() > 0);
    }

    @Test
    void findABeverageByIdTest() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        assertFalse(beverages.isEmpty());
        int beverageId = beverages.get(0).getBeverageId();

        Beverage testBeverage = beverageDaoJdbc.findBeverageById(beverageId).get();
        assertEquals(beverages.get(0).getBeverageTitle(), testBeverage.getBeverageTitle());
        assertTrue(testBeverage.getBeveragePrice() > 0);
        assertTrue(testBeverage.getBeverageQuantity() > 0);
    }

    @Test
    void createBeverageTest() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        assertFalse(beverages.isEmpty());
        Beverage testBeverage = new Beverage();
        testBeverage.setBeverageTitle("TEST BEVERAGE");
        beverageDaoJdbc.createBeverage(testBeverage);

        List<Beverage> testBeverages = beverageDaoJdbc.findAllBeverages();
        assertEquals((beverages.size()+1), testBeverages.size());
    }

    @Test
    void updateBeverageTest() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        assertFalse(beverages.isEmpty());
        Beverage beverage = beverages.get(0);
        beverage.setBeverageTitle("NEW_TITLE");
        beverageDaoJdbc.updateBeverage(beverage);

        List<Beverage> testBeverages = beverageDaoJdbc.findAllBeverages();
        Beverage testBeverage = testBeverages.get(0);
        assertTrue(beverage.getBeverageId() == testBeverage.getBeverageId());
        assertEquals("NEW_TITLE", testBeverage.getBeverageTitle());
    }

    @Test
    void deleteBeverageTest() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        assertFalse(beverages.isEmpty());
        Beverage beverage = beverages.get(0);
        beverageDaoJdbc.deleteBeverage(beverage.getBeverageId());

        List<Beverage> testBeverages = beverageDaoJdbc.findAllBeverages();
        assertFalse(testBeverages.isEmpty());
        assertTrue((beverages.size()-1) == testBeverages.size());

        beverageDaoJdbc.createBeverage(beverage);
    }
}