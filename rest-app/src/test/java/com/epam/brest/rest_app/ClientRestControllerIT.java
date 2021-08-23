package com.epam.brest.rest_app;

import com.epam.brest.model.Beverage;
import com.epam.brest.model.Ingredient;
import com.epam.brest.rest_app.exception.BeverageNotFoundAdvice;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
class ClientRestControllerIT {

    @Autowired
    private ClientRestController clientRestController;

    @Autowired
    private BeverageRestController beverageRestController;

    @Autowired
    private IngregientRestController ingregientRestController;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected MockIngredientService ingredientService = new MockIngredientService();

    protected MockBeverageService beverageService = new MockBeverageService();

    protected MockClientService clientService = new MockClientService();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(clientRestController, beverageRestController, ingregientRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void selectedBeveragesTest() throws Exception {
        List<Beverage> beverages = beverageService.findAllVisibleBeverages();
        assertNotNull(beverages);
        assertTrue(beverages.size() > 0);

        Beverage beverage = beverages.get(0);
        beverage.setBeverageHidden(false);
        beverageService.updateBeverage(beverage);

        List<Beverage> testBeverages = beverageService.findAllVisibleBeverages();
        assertNotNull(testBeverages);

        assertTrue(testBeverages.size() == (beverages.size() - 1));
    }

    @Test
    void updateIngredientsTest() throws Exception {
        List<Beverage> beverages = beverageService.findAllVisibleBeverages();

        List<Beverage> selectedBeverages = Arrays.asList(
                new Beverage(1, "Espresso", 30, 0, 0, 0, true, false, true, 0, 0, true),
                new Beverage(2, "Americano", 60, 0, 0, 90, true, false, false, 0, 0, true),
                new Beverage(3, "Mocachino", 30, 30, 30, 0, true, false, true, 0, 0, true)
        );
        List<Ingredient> ingredients = ingredientService.findAllIngredients();
        assertNotNull(ingredients);
        assertTrue(ingredients.size() == 9);

        clientRestController.updateIngredients(selectedBeverages);

        List<Ingredient> testIngredients = ingredientService.findAllIngredients();
        assertTrue(ingredients.get(0).getIngredientQuantity() == (testIngredients.get(0).getIngredientQuantity() + 120));
        assertTrue(ingredients.get(1).getIngredientQuantity() == (testIngredients.get(1).getIngredientQuantity() + 30));
        assertTrue(ingredients.get(2).getIngredientQuantity() == (testIngredients.get(2).getIngredientQuantity() + 30));
        assertTrue(ingredients.get(3).getIngredientQuantity() == (testIngredients.get(3).getIngredientQuantity() + 90));
        assertTrue(ingredients.get(4).getIngredientQuantity() > testIngredients.get(4).getIngredientQuantity());
        assertTrue(ingredients.get(5).getIngredientQuantity() == testIngredients.get(5).getIngredientQuantity());
        assertTrue(ingredients.get(6).getIngredientQuantity() > testIngredients.get(6).getIngredientQuantity());
        assertTrue(ingredients.get(7).getIngredientQuantity() == (testIngredients.get(7).getIngredientQuantity() + 3));
        assertTrue(ingredients.get(8).getIngredientQuantity() == (testIngredients.get(8).getIngredientQuantity() + 3));

        List<Beverage> testBeverages = beverageService.findAllVisibleBeverages();
        assertTrue(beverages.get(3).getBeverageQuantity() > testBeverages.get(3).getBeverageQuantity());
    }

    /////////////////////////////////////////////////////////////////////////////////

    private class MockIngredientService{
        List<Ingredient> findAllIngredients() throws Exception{
            MockHttpServletResponse response = mockMvc.perform(get("/ingredients")
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Ingredient>>() {});
        }
    }

    private class MockBeverageService{
        List<Beverage> findAllVisibleBeverages() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get("/client")
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Beverage>>() {});
        };

        Integer updateBeverage(Beverage beverage) throws Exception {
            MockHttpServletResponse response =
                    mockMvc.perform(put("/beverages")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(beverage))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        };
    }

    private class MockClientService{
        Integer updateIngredients(List<Beverage> selectedBeverages) throws Exception {
            MockHttpServletResponse response =
                    mockMvc.perform(put("/client")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(selectedBeverages))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        };
    }
}