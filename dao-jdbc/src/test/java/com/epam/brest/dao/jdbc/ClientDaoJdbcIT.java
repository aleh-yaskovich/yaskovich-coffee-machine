package com.epam.brest.dao.jdbc;

import com.epam.brest.model.Beverage;
import com.epam.brest.model.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db-config.xml","classpath:test-dao.xml"})
class ClientDaoJdbcIT {

    @Autowired
    private ClientDaoJdbc clientDaoJdbc;

    @Autowired
    private IngredientDaoJdbc ingredientDaoJdbc;

    private List<Beverage> selectedBeverages = Arrays.asList(
            new Beverage(1, "Espresso", 30, 0, 0, 0, true, false, true, 0, 0, true),
            new Beverage(2, "Americano", 60, 0, 0, 90, true, false, false, 0, 0, true),
            new Beverage(3, "Mocachino", 30, 30, 30, 0, true, true, true, 0, 0, true)
    );

    @Test
    void updateIngredientQuantityTest() {
        List<Ingredient> ingredients = ingredientDaoJdbc.findAllIngredients();
        int result = clientDaoJdbc.updateIngredientQuantity(selectedBeverages);
        assertTrue(result == 1);
        List<Ingredient> test = ingredientDaoJdbc.findAllIngredients();
        assertTrue((ingredients.get(0).getIngredientQuantity() - test.get(0).getIngredientQuantity()) == 120);
        assertTrue((ingredients.get(1).getIngredientQuantity() - test.get(1).getIngredientQuantity()) == 30);
        assertTrue((ingredients.get(2).getIngredientQuantity() - test.get(2).getIngredientQuantity()) == 30);
        assertTrue((ingredients.get(3).getIngredientQuantity() - test.get(3).getIngredientQuantity()) == 90);
        assertTrue((ingredients.get(4).getIngredientQuantity() - test.get(4).getIngredientQuantity()) == 9);
        assertTrue((ingredients.get(5).getIngredientQuantity() - test.get(5).getIngredientQuantity()) == 10);
        assertTrue((ingredients.get(6).getIngredientQuantity() - test.get(6).getIngredientQuantity()) == 4);
        assertTrue((ingredients.get(7).getIngredientQuantity() - test.get(7).getIngredientQuantity()) == 3);
        assertTrue((ingredients.get(8).getIngredientQuantity() - test.get(8).getIngredientQuantity()) == 3);
    }
}