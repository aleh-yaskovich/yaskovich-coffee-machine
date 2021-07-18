package com.epam.brest.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IngredientController {

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
