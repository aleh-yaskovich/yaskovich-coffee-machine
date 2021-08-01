package com.epam.brest.service.impl;

import com.epam.brest.dao.jdbc.IngredientDaoJdbc;
import com.epam.brest.model.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Mock
    private IngredientDaoJdbc ingredientDao;

    private Ingredient ingredient = new Ingredient();
    private List<Ingredient> ingredientList = Collections.singletonList(ingredient);

    @Test
    void findAllIngredientsTest() {
        Mockito.when(ingredientDao.findAllIngredients()).thenReturn(ingredientList);
        List<Ingredient> ingredients = ingredientService.findAllIngredients();
        assertNotNull(ingredients);
        assertFalse(ingredients.isEmpty());
        assertEquals(ingredients.get(0), ingredient);
    }

    @Test
    void findIngredientByIdTest() {
        Mockito.when(ingredientDao.findIngredientById(1)).thenReturn(Optional.ofNullable(ingredientList.get(0)));
        Ingredient ingredient = ingredientService.findIngredientById(1);
        assertEquals(ingredientList.get(0), ingredient);
    }

    @Test
    void findIngredientByIdTestWithException() {
        int ingredientId = 99;
        Mockito.when(ingredientDao.findIngredientById(any())).thenReturn(Optional.empty());
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ingredientService.findIngredientById(ingredientId);} );
        assertEquals("Ingredient with ID " + ingredientId + " is not found", exception.getMessage());
    }

    @Test
    void updateIngredientTest() {
        Mockito.when(ingredientDao.updateIngredient(any())).thenReturn(1);
        int result = ingredientService.updateIngredient(new Ingredient());
        assertTrue(result == 1);

        Mockito.when(ingredientDao.updateIngredient(any())).thenReturn(0);
        result = ingredientService.updateIngredient(new Ingredient());
        assertTrue(result == 0);
    }
}