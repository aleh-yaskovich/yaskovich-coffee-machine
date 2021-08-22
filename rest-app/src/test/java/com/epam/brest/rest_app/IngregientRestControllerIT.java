package com.epam.brest.rest_app;

import com.epam.brest.model.Ingredient;
import com.epam.brest.rest_app.exception.IngredientNotFoundAdvice;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
class IngregientRestControllerIT {

    public static final String INGREDIENTS_ENDPOINT = "/ingredients";

    @Autowired
    private IngregientRestController ingregientRestController;

    @Autowired
    private IngredientNotFoundAdvice ingredientNotFoundAdvice;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected MockIngredientService ingredientService = new MockIngredientService();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(ingregientRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(ingredientNotFoundAdvice)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ingredientsTest() throws Exception {
        List<Ingredient> ingredients = ingredientService.findAllIngredients();
        assertNotNull(ingredients);
        assertTrue(ingredients.size() > 0);
    }

    @Test
    void ingredientByIdTest() throws Exception {
        List<Ingredient> ingredients = ingredientService.findAllIngredients();
        assertNotNull(ingredients);
        assertTrue(ingredients.size() > 0);

        Integer ingredientId = ingredients.get(0).getIngredientId();
        Ingredient testIngredient = ingredientService.findIngredientById(ingredientId);
        assertEquals(testIngredient.getIngredientTitle(), ingredients.get(0).getIngredientTitle());
        assertTrue(ingredientId == testIngredient.getIngredientId());
    }

    @Test
    public void returnNotFoundOnMissedIngredient() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(
                                INGREDIENTS_ENDPOINT + "/999")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andReturn().getResponse();
        assertNotNull(response);
    }

    @Test
    void ingredientUpdate() throws Exception {
        List<Ingredient> ingredients = ingredientService.findAllIngredients();
        assertNotNull(ingredients);
        assertTrue(ingredients.size() > 0);

        Ingredient ingredient = ingredients.get(0);
        int quantity = ingredient.getIngredientQuantity();
        ingredient.setIngredientQuantity(quantity + 1);
        Integer id = ingredientService.updateIngredient(ingredient);

        List<Ingredient>testIngredients = ingredientService.findAllIngredients();
        Ingredient testIngredient = testIngredients.get(0);

        assertTrue(testIngredient.getIngredientQuantity() == quantity + 1);
        assertTrue(testIngredient.getIngredientId() == ingredient.getIngredientId());
        assertEquals(testIngredient.getIngredientTitle(), ingredient.getIngredientTitle());
    }

    /////////////////////////////////////////////////////////////////////////////////

    private class MockIngredientService{
        List<Ingredient> findAllIngredients() throws Exception{
            MockHttpServletResponse response = mockMvc.perform(get(INGREDIENTS_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Ingredient>>() {});
        }

        Ingredient findIngredientById(Integer ingredientId) throws Exception{
            MockHttpServletResponse response = mockMvc.perform(get(INGREDIENTS_ENDPOINT + "/" + ingredientId)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Ingredient.class);
        }


        Integer updateIngredient(Ingredient ingredient) throws Exception{
            MockHttpServletResponse response =
                    mockMvc.perform(post(INGREDIENTS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(ingredient))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }
}