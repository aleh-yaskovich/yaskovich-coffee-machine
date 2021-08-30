package com.epam.brest.web_app;

import com.epam.brest.model.Beverage;
import com.epam.brest.service.rest.BeverageServiceRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BeverageController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BeverageController.class);

    private final BeverageServiceRest beverageService;

    public String message;

    public BeverageController(BeverageServiceRest beverageService) {
        this.beverageService = beverageService;
    }


    /**
     * Show beverage list
     * */
    @GetMapping("/beverages")
    public String showAllBeverages(Model model) {
        LOGGER.debug("showAllBeverages()");
        model.addAttribute("beverages", beverageService.findAllBeverages());
        model.addAttribute("message", message);
        message = null;
        return "beverages";
    }

    /**
     * Show form to create new beverage
     * */
    @GetMapping("/beverage")
    public String showCreateNewBeveragePage(Model model) {
        LOGGER.debug("showCreateNewBeveragePage()");
        model.addAttribute("beverage", new Beverage());
        model.addAttribute("isNew", true);
        return "beverage";
    }

    /**
     * Sends completed form and makes redirect to Beverages
     */
    @PostMapping(value = "/beverage")
    public String createNewBeverage(Beverage beverage, Model model) {
        LOGGER.debug("createNewBeverage({})", beverage);
        try {
            beverageService.createBeverage(beverage);
            message = "The beverage created successfully!";
        } catch (IllegalArgumentException ex) {
            message = ex.getMessage();
        } finally {
            model.addAttribute("beverages", beverageService.findAllBeverages());
            return "redirect:/beverages";
        }
    }

    /**
     * Show form to update selected beverage
     * */
    @GetMapping("/beverage/{id}")
    public String showUpdateBeveragePage(@PathVariable Integer id, Model model) {
        LOGGER.debug("showUpdateBeveragePage({})", id);
        try {
            Beverage beverage = beverageService.findBeverageById(id);
            String message = "Update beverage «"+beverage.getBeverageTitle()+"»";
            model.addAttribute("message", message);
            model.addAttribute("beverage", beverage);
            model.addAttribute("isNew", false);
            return "beverage";
        } catch (IllegalArgumentException ex) {
            message = ex.getMessage();
            model.addAttribute("beverages", beverageService.findAllBeverages());
            return "redirect:/beverages";
        }
    }

    /**
     * Sends completed form and makes redirect to Beverages
     * */
    @PostMapping(value = "/beverage/{id}")
    public String updateBeverage(Beverage beverage, Model model) {
        LOGGER.debug("updateBeverage({})", beverage);
        beverageService.updateBeverage(beverage);
        message = "The beverage updated successfully!";
        return "redirect:/beverages";
    }

    /**
     * Sends Beverage's ID to delete and makes redirect to Beverages
     */
    @GetMapping(value = "/beverage/{id}/delete")
    public String deleteBeverageById(@PathVariable Integer id, Model model) {
        LOGGER.debug("deleteBeverageById({})", id, model);
        if(beverageService.deleteBeverage(id) == 1)
            message = "The beverage deleted successfully!";
        else if(beverageService.deleteBeverage(id) == 0)
            message = "The beverage is not deleted!";
        else message = "Something is wrong";
        return "redirect:/beverages";
    }
}
