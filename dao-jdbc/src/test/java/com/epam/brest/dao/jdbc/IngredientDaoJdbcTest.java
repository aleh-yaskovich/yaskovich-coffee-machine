package com.epam.brest.dao.jdbc;

import com.epam.brest.model.Ingredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db-config.xml","classpath:dao-context.xml"})
class IngredientDaoJdbcTest {

    @Autowired
    private IngredientDaoJdbc ingredientDaoJdbc;

    @Test
    void findAllIngredientsTestNotNull() {
        List<Ingredient> ingredients = ingredientDaoJdbc.findAllIngredients();
        Assertions.assertNotNull(ingredients);
    }

    @Test
    void findAllIngredientsTestNotEmpty() {
        List<Ingredient> ingredients = ingredientDaoJdbc.findAllIngredients();
        Assertions.assertFalse(ingredients.isEmpty());
    }

    @Test
    void findIngredientByIdTest() {
        List<Ingredient> ingredients = ingredientDaoJdbc.findAllIngredients();
        Assertions.assertFalse(ingredients.isEmpty());

        Ingredient ingredient = ingredients.get(0);
        Ingredient testIngredient = ingredientDaoJdbc.findIngredientById(1).get();
        Assertions.assertEquals(ingredient.getIngredientTitle(), testIngredient.getIngredientTitle());

        ingredient = ingredients.get(1);
        testIngredient = ingredientDaoJdbc.findIngredientById(ingredient.getIngredientId()).get();
        Assertions.assertEquals(2.50, testIngredient.getIngredientPrice());
    }

    @Test
    void updateIngredientTest() {
        List<Ingredient> ingredients = ingredientDaoJdbc.findAllIngredients();
        Assertions.assertFalse(ingredients.isEmpty());

        Ingredient ingredient = ingredients.get(0);
        ingredient.setIngredientTitle("TEST");
        ingredientDaoJdbc.updateIngredient(ingredient);

        Ingredient testIngredient = ingredientDaoJdbc.findIngredientById(ingredient.getIngredientId()).get();
        Assertions.assertEquals("TEST", testIngredient.getIngredientTitle());
    }
}