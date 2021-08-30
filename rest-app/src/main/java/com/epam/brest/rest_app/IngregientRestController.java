package com.epam.brest.rest_app;

import com.epam.brest.model.Ingredient;
import com.epam.brest.service.impl.IngredientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class IngregientRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngregientRestController.class);

    private final IngredientServiceImpl ingredientService;

    public IngregientRestController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
    * return collection of all ingredients
    * curl -v localhost:8080/ingredients
    **/
    @GetMapping(value = "/ingredients")
    public Collection<Ingredient> ingredients() {
        LOGGER.debug("ingredients()");
        return ingredientService.findAllIngredients();
    }

    /**
     * return ingredient by Id
     * curl -v localhost:8080/ingredients/1
     **/
    @GetMapping(value = "/ingredients/{id}")
    public ResponseEntity<Ingredient> ingredient(@PathVariable Integer id) {
        LOGGER.debug("ingredient({id})", id);
        try {
            return new ResponseEntity<>(ingredientService.findIngredientById(id), HttpStatus.OK);
        } catch(IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * update selected ingredient
     * curl -X POST localhost:8080/ingredients -H 'Content-type:application/json' -d '{"ingredientId":1,
     * "ingredientTitle":"Coffee","ingredientQuantity":5000,"ingredientExpirationDate":"2021-12-31",
     * "ingredientPrice":31.5,"ingredientRequired":true}'
     **/
    @PutMapping(value = "/ingredients", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateIngredient(@RequestBody Ingredient ingredient) {
        LOGGER.debug("updateIngredient({ingredient})", ingredient);
        Integer res = ingredientService.updateIngredient(ingredient);
        return res > 0
                ? new ResponseEntity<>(res, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Calculate prices for optional ingredients
     * curl -v localhost:8080/prices
     */
    @GetMapping(value = "/prices")
    List<Double> getOptionalIngredientsPrices() {
        LOGGER.debug("getOptionalIngredientsPrices()");
        return ingredientService.getOptionalIngredientsPrices();
    };
}
