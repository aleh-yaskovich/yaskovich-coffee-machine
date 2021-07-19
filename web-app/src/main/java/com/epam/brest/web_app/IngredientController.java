package com.epam.brest.web_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IngredientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(IngredientController.class);

    /**
     * Show ingredients list
     * */
    @GetMapping("/ingredients")
    public String showAllIngredients() {
        return "ingredients";
    }

    /**
     * Show form to update selected ingredient
     * */
    @GetMapping("/ingredient/{id}")
    public String showUpdateIngredientPage() {
        return "ingredient";
    }

}
