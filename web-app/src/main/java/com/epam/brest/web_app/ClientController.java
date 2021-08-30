package com.epam.brest.web_app;

import com.epam.brest.model.Beverage;
import com.epam.brest.model.Client;
import com.epam.brest.service.rest.BeverageServiceRest;
import com.epam.brest.service.rest.ClientServiceRest;
import com.epam.brest.service.rest.IngredientServiceRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ClientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    private final BeverageServiceRest beverageService;
    private final ClientServiceRest clientService;
    private final IngredientServiceRest ingredientService;

    private Client client = new Client();
    private int count = 1;
    public String message;

    public ClientController(BeverageServiceRest beverageService, ClientServiceRest clientService, IngredientServiceRest ingredientServiceRest) {
        this.beverageService = beverageService;
        this.clientService = clientService;
        this.ingredientService = ingredientServiceRest;
    }

    /**
     * Show Client page
     **/
    @GetMapping(value = "/client")
    public String showClientPage(Model model) {
        LOGGER.debug("showClientPage()");
        double[] ingPriceArr = new double[3];
        List<Double> listDouble = ingredientService.getOptionalIngredientsPrices();
        if(listDouble.size() == 3) {
            for(int i = 0; i < 3; i++)
                ingPriceArr[i] = listDouble.get(i);
        }

        model.addAttribute("message", message);
        model.addAttribute("isActive", (this.client.getTotalPrice() == 0.0));
        model.addAttribute("client", new Client());
        model.addAttribute("newBeverage", new Beverage());
        model.addAttribute("beverages", beverageService.findVisibleBeverages());
        model.addAttribute("selectedBeverages", this.client.getSelectedBeverages());
        model.addAttribute("credit", String.format("%.2f", this.client.getClientCredit()));
        model.addAttribute("totalPrice", String.format("%.2f", this.client.getTotalPrice()));
        model.addAttribute("optionalIngPrice", Arrays.stream(ingPriceArr).toArray());
        message = null;
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
        LOGGER.debug("addBeverage({id},{newBeverage})", id, newBeverage);
        try{
            // find selected beverage by Id
            Beverage selectedBeverage = beverageService.findBeverageById(id);
            selectedBeverage.setBeverageIngSugar(false);
            selectedBeverage.setBeverageIngSyrup(false);
            selectedBeverage.setBeverageIngCinnamon(false);
            // calculate price of optional ingredients and
            // create string of optional ingredients
            double optionalIngPrice = 0.0;
            String optionalIngredients = "";
            List<Double> ingPriceArr = ingredientService.getOptionalIngredientsPrices();
            if(newBeverage.isBeverageIngSugar()) {
                optionalIngPrice += ingPriceArr.get(0);
                optionalIngredients += " + sugar";
                selectedBeverage.setBeverageIngSugar(true);
            }
            if(newBeverage.isBeverageIngSyrup()) {
                optionalIngPrice += ingPriceArr.get(1);
                optionalIngredients += " + syrup";
                selectedBeverage.setBeverageIngSyrup(true);
            }
            if(newBeverage.isBeverageIngCinnamon()) {
                optionalIngPrice += ingPriceArr.get(2);
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
            message = ex.getMessage();
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
            message = "Enjoy your beverage!";
        } else {
            message = "You don't have enough credit. Press +Add to increase your credit";
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
     * Round a fractional number to two decimal places
     **/
    private double roundNumberTo2(double d) {
        double scale = Math.pow(10, 2);
        double result = Math.ceil(d * scale) / scale;
        return result;
    }
}
