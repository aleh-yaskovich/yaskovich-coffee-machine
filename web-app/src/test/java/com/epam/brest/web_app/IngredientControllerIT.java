package com.epam.brest.web_app;

import com.epam.brest.model.Ingredient;
import com.epam.brest.service.IngredientService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = WebAppTestConfig.class)
@Disabled
@WebMvcTest(IngredientController.class)
class IngredientControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Test
    void showAllIngredientsTest() throws Exception {
        Ingredient ing1 = createIngredient(1, "Coffee");
        Ingredient ing2 = createIngredient(2, "Milk");
        Ingredient ing3 = createIngredient(3, "Chocolate");
        when(ingredientService.findAllIngredients()).thenReturn(Arrays.asList(ing1, ing2, ing3));
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/ingredients")
//                ).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("departments"))
//                .andExpect(model().attribute("departments", hasItem(
//                        allOf(
//                                hasProperty("departmentId", is(d1.getDepartmentId())),
//                                hasProperty("departmentName", is(d1.getDepartmentName())),
//                                hasProperty("avgSalary", is(d1.getAvgSalary()))
//                        )
//                )))
//                .andExpect(model().attribute("departments", hasItem(
//                        allOf(
//                                hasProperty("departmentId", is(d2.getDepartmentId())),
//                                hasProperty("departmentName", is(d2.getDepartmentName())),
//                                hasProperty("avgSalary", is(d2.getAvgSalary()))
//                        )
//                )))
//                .andExpect(model().attribute("departments", hasItem(
//                        allOf(
//                                hasProperty("departmentId", is(d3.getDepartmentId())),
//                                hasProperty("departmentName", is(d3.getDepartmentName())),
//                                hasProperty("avgSalary", isEmptyOrNullString())
//                        )
//                )));
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

    private Ingredient createIngredient(Integer id, String title) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(id);
        ingredient.setIngredientTitle(title);
        return ingredient;
    }
}