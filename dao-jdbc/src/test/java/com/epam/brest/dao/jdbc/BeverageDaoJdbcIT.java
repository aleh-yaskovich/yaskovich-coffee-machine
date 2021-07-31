package com.epam.brest.dao.jdbc;

import com.epam.brest.model.Beverage;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertNotNull(beverages);
    }

    @Test
    void findAllBeveragesTestNotEmpty() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        Assertions.assertFalse(beverages.isEmpty());
    }

    @Test
    void findVisibleBeveragesTest() {
        List<Beverage> beverages = beverageDaoJdbc.findVisibleBeverages();
        Assertions.assertFalse(beverages.isEmpty());
        Beverage beverage = beverages.get(0);
        beverage.setBeverageHidden(false);
        beverageDaoJdbc.updateBeverage(beverage);
        List<Beverage> testBeverages = beverageDaoJdbc.findVisibleBeverages();
        Assertions.assertTrue((beverages.size()-1) == testBeverages.size());
    }

    @Test
    void findABeverageByIdTest() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        Assertions.assertFalse(beverages.isEmpty());
        int beverageId = beverages.get(0).getBeverageId();

        Beverage testBeverage = beverageDaoJdbc.findBeverageById(beverageId).get();
        Assertions.assertEquals(beverages.get(0).getBeverageTitle(), testBeverage.getBeverageTitle());
    }

    @Test
    void createBeverageTest() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        Assertions.assertFalse(beverages.isEmpty());
        Beverage testBeverage = new Beverage();
        testBeverage.setBeverageTitle("TEST BEVERAGE");
        beverageDaoJdbc.createBeverage(testBeverage);

        List<Beverage> testBeverages = beverageDaoJdbc.findAllBeverages();
        Assertions.assertEquals((beverages.size()+1), testBeverages.size());
    }

    @Test
    void updateBeverageTest() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        Assertions.assertFalse(beverages.isEmpty());
        Beverage beverage = beverages.get(0);
        beverage.setBeverageTitle("NEW_TITLE");
        beverageDaoJdbc.updateBeverage(beverage);

        List<Beverage> testBeverages = beverageDaoJdbc.findAllBeverages();
        Beverage testBeverage = testBeverages.get(0);
        Assertions.assertTrue(beverage.getBeverageId() == testBeverage.getBeverageId());
        Assertions.assertEquals("NEW_TITLE", testBeverage.getBeverageTitle());
    }

    @Test
    void deleteBeverageTest() {
        List<Beverage> beverages = beverageDaoJdbc.findAllBeverages();
        Assertions.assertFalse(beverages.isEmpty());
        Beverage beverage = beverages.get(0);
        beverageDaoJdbc.deleteBeverage(beverage.getBeverageId());

        List<Beverage> testBeverages = beverageDaoJdbc.findAllBeverages();
        Assertions.assertFalse(testBeverages.isEmpty());
        Assertions.assertTrue((beverages.size()-1) == testBeverages.size());

        beverageDaoJdbc.createBeverage(beverage);
    }
}