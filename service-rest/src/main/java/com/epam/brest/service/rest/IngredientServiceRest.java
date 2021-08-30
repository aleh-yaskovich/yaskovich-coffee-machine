package com.epam.brest.service.rest;

import com.epam.brest.model.Ingredient;
import com.epam.brest.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class IngredientServiceRest implements IngredientService {

    private final static Logger LOGGER = LoggerFactory.getLogger(IngredientServiceRest.class);

    private String url = "http://localhost:8080/ingredients";
    private RestTemplate restTemplate;

    public IngredientServiceRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Ingredient> findAllIngredients() {
        LOGGER.debug("findAllIngredients()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Ingredient>) responseEntity.getBody();
    }

    @Override
    public Ingredient findIngredientById(Integer ingredientId) {
        LOGGER.debug("findIngredientById({ingredientId})", ingredientId);
        ResponseEntity<Ingredient> responseEntity =
                restTemplate.getForEntity(url + "/" + ingredientId, Ingredient.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer updateIngredient(Ingredient ingredient) {
        LOGGER.debug("updateIngredient({ingredient})", ingredient);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Ingredient> entity = new HttpEntity<>(ingredient, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public List<Double> getOptionalIngredientsPrices() {
        LOGGER.debug("getOptionalIngredientsPrices()");
        ResponseEntity responseEntity = restTemplate.getForEntity("http://localhost:8080/prices", List.class);
        return (List<Double>) responseEntity.getBody();
    }
}
