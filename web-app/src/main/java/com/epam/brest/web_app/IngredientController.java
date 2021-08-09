package com.epam.brest.web_app;

import com.epam.brest.model.Beverage;
import com.epam.brest.model.Ingredient;
import com.epam.brest.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(IngredientController.class);

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Show ingredients list
     * */
    @GetMapping("/ingredients")
    public String showAllIngredients(String message, Model model) {
        LOGGER.debug("showAllIngredients()");
        model.addAttribute("ingredients", ingredientService.findAllIngredients());
        model.addAttribute("message", message);
        return "ingredients";
    }

    /**
     * Show form to update selected ingredient
     * */
    @GetMapping("/ingredient/{id}")
    public String showUpdateIngredientPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("showUpdateIngredientPage({})", id);
        try {
            Ingredient ingredient = ingredientService.findIngredientById(id);
            model.addAttribute("ingredient", ingredient);
            return "ingredient";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("message", ex.getMessage());
            return "ingredients";
        }
    }

    /**
     * Sends completed form and makes redirect to Ingredients
     * */
    @PostMapping(value = "/ingredient/{id}")
    public String updateIngredient(Ingredient ingredient, Model model) {
        LOGGER.debug("updateIngredient({})", ingredient);
        ingredientService.updateIngredient(ingredient);
        model.addAttribute("message", "The ingredient updated successfully!");
        return "redirect:/ingredients";
    }
}
