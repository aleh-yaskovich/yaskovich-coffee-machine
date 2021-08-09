package com.epam.brest.web_app;

import com.epam.brest.model.Beverage;
import com.epam.brest.model.Client;
import com.epam.brest.model.Ingredient;
import com.epam.brest.service.BeverageService;
import com.epam.brest.service.ClientService;
import com.epam.brest.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    private final IngredientService ingredientService;
    private final BeverageService beverageService;
    private final ClientService clientService;


    private Client client = new Client();
    private int count = 1;

    public ClientController(IngredientService ingredientService, BeverageService beverageService, ClientService clientService) {
        this.ingredientService = ingredientService;
        this.beverageService = beverageService;
        this.clientService = clientService;
    }

    /**
     * Show Client page
     **/
    @GetMapping(value = "/client")
    public String showClientPage(String message, Model model) {
        LOGGER.debug("showClientPage()");
        double[] ingPriceArr = optionalIngredientsPrice();

        model.addAttribute("message", message);
        model.addAttribute("isActive", (this.client.getTotalPrice() == 0.0));
        model.addAttribute("client", new Client());
        model.addAttribute("newBeverage", new Beverage());
        model.addAttribute("beverages", beverageService.findVisibleBeverages());
        model.addAttribute("selectedBeverages", this.client.getSelectedBeverages());
        model.addAttribute("credit", String.format("%.2f", this.client.getClientCredit()));
        model.addAttribute("totalPrice", String.format("%.2f", this.client.getTotalPrice()));
        model.addAttribute("optionalIngPrice", ingPriceArr);
        return "client";
    }

    /**
     * Increase Client Credit
     **/
    @PostMapping(value = "/client")
    public String increaseCredit(Client client, Model model) {
        LOGGER.debug("increaseCredit()");
        double credit = this.client.getClientCredit() + client.getClientCredit();
        this.client.setClientCredit(credit);
        return "redirect:/client";
    }

    /**
     * Add beverage to List of selected beverages
     **/
    @PostMapping(value = "/client/{id}")
    public String addBeverage(@PathVariable Integer id, Beverage newBeverage, Model model) {
        LOGGER.debug("addBeverage({},{})", id, newBeverage);
        try{
            // find selected beverage by Id
            Beverage selectedBeverage = beverageService.findBeverageById(id);
            // calculate price of optional ingredients and
            // create string of optional ingredients
            double optionalIngPrice = 0.0;
            String optionalIngredients = "";
            double[] ingPriceArr = optionalIngredientsPrice();
            if(newBeverage.isBeverageIngSugar()) {
                optionalIngPrice += ingPriceArr[0];
                optionalIngredients += " + sugar";
                selectedBeverage.setBeverageIngSugar(true);
            }
            if(newBeverage.isBeverageIngSyrup()) {
                optionalIngPrice += ingPriceArr[1];
                optionalIngredients += " + syrup";
                selectedBeverage.setBeverageIngSyrup(true);
            }
            if(newBeverage.isBeverageIngCinnamon()) {
                optionalIngPrice += ingPriceArr[2];
                optionalIngredients += " + cinnamon";
                selectedBeverage.setBeverageIngCinnamon(true);
            }
            // change Id, Title and Price of selected beverage
            selectedBeverage.setBeverageId(this.count++);
            selectedBeverage.setBeverageTitle(selectedBeverage.getBeverageTitle() + optionalIngredients);
            selectedBeverage.setBeveragePrice(roundNumberTo2(selectedBeverage.getBeveragePrice() + optionalIngPrice));
            // add beverage to List of selected beverages
            List<Beverage> selectedList = this.client.getSelectedBeverages();
            selectedList.add(selectedBeverage);
            this.client.setSelectedBeverages(selectedList);
            this.client.setTotalPrice(calculateTotalPrice(selectedList));
        } catch (IllegalArgumentException ex) {
            model.addAttribute("message", ex.getMessage());
        }
        return "redirect:/client";
    }

    /**
     * Delete beverage from List of selected beverages
     **/
    @GetMapping(value = "/delete/{beverageId}")
    public String deleteSelectedBeverage(@PathVariable int beverageId, Model model) {
        LOGGER.debug("deleteSelectedBeverage({})", beverageId);
        // delete beverage from List of selected beverages
        List<Beverage> selectedList = this.client.getSelectedBeverages();
        int del = -1;
        for(int i = 0; i < selectedList.size(); i++) {
            if(selectedList.get(i).getBeverageId() == beverageId) {
                del = i;
                break;
            }
        }
        if(del != -1)
            selectedList.remove(del);
        this.client.setSelectedBeverages(selectedList);
        // change total price
        this.client.setTotalPrice(calculateTotalPrice(selectedList));
        return "redirect:/client";
    }

    /**
     * If all is OK, this method makes order, decreases quantity of ingredients and client credit
     **/
    @GetMapping(value="/order")
    public String makePayment(Model model) {
        LOGGER.debug("makePayment({})");
        List<Beverage> selectedList = this.client.getSelectedBeverages();
        double totalPrice = calculateTotalPrice(selectedList);
        if(totalPrice <= this.client.getClientCredit()) {
            this.client.setClientCredit(this.client.getClientCredit() - totalPrice);
            this.client.setTotalPrice(0);
            clientService.updateIngredientQuantity(selectedList);
            this.client.setSelectedBeverages(new ArrayList<>());
            model.addAttribute("message", "Enjoy your beverage!");
        } else {
            model.addAttribute("message", "You don't have enough credit. Press + Add to increase your credit");
        }
        return "redirect:/client";
    }

    /**
     * Calculate price of all selected beverages
     **/
    private double calculateTotalPrice(List<Beverage> selectedBeverages) {
        double totalPrice = 0;
        for(Beverage beverage : selectedBeverages)
            totalPrice += beverage.getBeveragePrice();
        return roundNumberTo2(totalPrice);
    }

    /**
     * Calculate price of non-required ingredients
     **/
    private double[] optionalIngredientsPrice() {
        // We can add 3 g. of sugar, 10 g. of syrup and 2 g. of cinnamon to every beverage
        List<Ingredient> ingredients = ingredientService.findAllIngredients();
        double[] optionalIngredientsPrice = {
                roundNumberTo2(ingredients.get(4).getIngredientPrice()/1000*3),
                roundNumberTo2(ingredients.get(5).getIngredientPrice()/1000*10),
                roundNumberTo2(ingredients.get(6).getIngredientPrice()/1000*2)
        };
        return optionalIngredientsPrice;
    }

    /**
     * Round a fractional number to two decimal places
     **/
    private double roundNumberTo2(double d) {
        double scale = Math.pow(10, 2);
        double result = Math.ceil(d * scale) / scale;
        return result;
    }
}
