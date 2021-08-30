package com.epam.brest.service.rest;

import com.epam.brest.model.Ingredient;
import com.epam.brest.service.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class IngredientServiceRestTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IngredientService ingredientService;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    private String url = "http://localhost:8080/ingredients";

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void findAllIngredientsTest() throws Exception {
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(
                                new Ingredient(),
                                new Ingredient())))
                );

        // when
        List<Ingredient> ingredients = ingredientService.findAllIngredients();

        // then
        mockServer.verify();
        assertNotNull(ingredients);
        assertTrue(ingredients.size() > 0);
    }

    @Test
    public void findIngredientByIdTest() throws Exception {
        // given
        Integer id = 1;
        Ingredient ingredient = new Ingredient(1, "TEST", 1000, Date.valueOf("2021-08-24"), 5.0, true);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url+"/"+id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(ingredient))
                );

        // when
        Ingredient ingredientRes = ingredientService.findIngredientById(id);

        // then
        mockServer.verify();
        assertEquals(ingredientRes.getIngredientId(), id);
        assertEquals(ingredientRes.getIngredientTitle(), ingredient.getIngredientTitle());
    }

    @Test
    public void updateIngredientTest() throws Exception {
        // given
        Integer id = 1;
        Ingredient ingredient = new Ingredient(1, "TEST", 1000, Date.valueOf("2021-08-24"), 5.0, true);


        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url+"/"+id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(ingredient))
                );

        // when
        int result = ingredientService.updateIngredient(ingredient);
        Ingredient ingredientRes = ingredientService.findIngredientById(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertEquals(ingredientRes.getIngredientId(), id);
        assertEquals(ingredientRes.getIngredientTitle(), ingredient.getIngredientTitle());
    }
}