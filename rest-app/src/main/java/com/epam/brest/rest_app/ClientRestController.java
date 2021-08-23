package com.epam.brest.rest_app;

import com.epam.brest.model.Beverage;
import com.epam.brest.service.impl.BeverageServiceImpl;
import com.epam.brest.service.impl.ClientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class ClientRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestController.class);

    private final ClientServiceImpl clientService;

    private final BeverageServiceImpl beverageService;

    public ClientRestController(ClientServiceImpl clientService, BeverageServiceImpl beverageService) {
        this.clientService = clientService;
        this.beverageService = beverageService;
    }

    /**
     * Return list with all visible beverages
     * curl -v localhost:8080/client
     */
    @GetMapping(value = "/client")
    Collection<Beverage> selectedBeverages() {
        LOGGER.debug("selectedBeverages()");
        return beverageService.findVisibleBeverages();
    };

    /**
     * Update quantity of ingredients
     * curl -X PUT localhost:8080/client -H 'Content-type:application/json' -d '[{"beverageId":1,"beverageTitle":"Espresso",
     * "beverageIngCoffee":30,"beverageIngMilk":0,"beverageIngChocolate":0,"beverageIngWater":0,"beverageIngSugar":true,
     * "beverageIngSyrup":false,"beverageIngCinnamon":true,"beveragePrice":0.99,"beverageQuantity":83,"beverageHidden":true},
     * {"beverageId":2,"beverageTitle":"Double Espresso","beverageIngCoffee":60,"beverageIngMilk":0,"beverageIngChocolate":0,
     * "beverageIngWater":0,"beverageIngSugar":true,"beverageIngSyrup":false,"beverageIngCinnamon":true,"beveragePrice":1.94,
     * "beverageQuantity":41,"beverageHidden":true}]'
     */
    @PutMapping(value = "/client", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<Integer> updateIngredients(@RequestBody List<Beverage> selectedBeverages) {
        LOGGER.debug("updateIngredients({selectedBeverages})", selectedBeverages);
        Integer res = clientService.updateIngredientQuantity(selectedBeverages);
        return res > 0
                ? new ResponseEntity<>(res, HttpStatus.OK)
                : new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    };
}
