package com.epam.brest.web_app;

import com.epam.brest.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:web-context-test.xml")
class IngredientControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void givenWac_whenServletContext() {
        ServletContext servletContext = wac.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
    }

    @Test
    void showAllIngredientsTest() throws Exception {
        mockMvc.perform(get("/ingredients")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("ingredients"));
    }

    @Test
    void showUpdateIngredientPageTest_withId_1() throws Exception {
        mockMvc.perform(get("/ingredient/1")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("ingredient"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    void showUpdateIngredientPageTest_withId_10() throws Exception {
        mockMvc.perform(get("/ingredient/10")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("ingredients"))
                .andExpect(model().attribute("message", "Ingredient with ID 10 is not found"));
    }

    @Test
    void updateBeverageTest() throws Exception {
        mockMvc.perform(post("/ingredient/1")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(view().name("redirect:/ingredients"))
                .andExpect(model().attribute("message", "The ingredient updated successfully!"));
    }
}