package com.epam.brest.web_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeverageController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BeverageController.class);

    /**
     * Show beverage list
     * */
    @GetMapping("/beverages")
    public String showAllBeverages() {
        return "beverages";
    }

    /**
     * Show form to create new beverage
     * */
    @GetMapping("/beverage")
    public String showCreateNewBeveragePage() {
        return "beverage";
    }

    /**
     * Show form to update selected beverage
     * */
    @GetMapping("/beverage/{id}")
    public String showUpdateBeveragePage() {
        return "beverage";
    }

}
