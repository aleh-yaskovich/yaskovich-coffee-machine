package com.epam.brest.rest_app;

import com.epam.brest.model.Beverage;
import com.epam.brest.service.impl.BeverageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class BeverageRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeverageRestController.class);

    private final BeverageServiceImpl beverageService;

    public BeverageRestController(BeverageServiceImpl beverageService) {
        this.beverageService = beverageService;
    }

    /**
     * Return list with all beverages
     * curl -v localhost:8080/beverages
     */
    @GetMapping(value = "/beverages")
    Collection<Beverage> beverages() {
        LOGGER.debug("beverages()");
        return beverageService.findAllBeverages();
    };

    /**
     * Return selected beverage by ID
     * curl -v localhost:8080/beverages/1
     */
    @GetMapping(value = "/beverages/{id}")
    ResponseEntity<Beverage> beverage(@PathVariable Integer id) {
        LOGGER.debug("beverage({id})", id);
        try {
            return new ResponseEntity<>(beverageService.findBeverageById(id), HttpStatus.OK);
        } catch(IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    /**
     * Create new beverage
     * curl -X POST localhost:8080/beverages -H 'Content-type:application/json' -d '{"beverageId":10,
     * "beverageTitle":"TEST","beverageIngCoffee":10,"beverageIngMilk":10,"beverageIngChocolate":10,"beverageIngWater":10,
     * "beverageIngSugar":true,"beverageIngSyrup":false,"beverageIngCinnamon":true,"beveragePrice":0.0,"beverageQuantity":0,
     * "beverageHidden":true}'
     */
    @PostMapping(value = "/beverages", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> create(@RequestBody Beverage beverage) {
        LOGGER.debug("create({beverage})", beverage);
        Integer id = beverageService.createBeverage(beverage);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    /**
     * Update selected beverage
     * curl -X PUT localhost:8080/beverages -H 'Content-type:application/json' -d '{"beverageId":1,"beverageTitle":"UPDATE",
     * "beverageIngCoffee":30,"beverageIngMilk":0,"beverageIngChocolate":0,"beverageIngWater":0,"beverageIngSugar":true,
     * "beverageIngSyrup":false,"beverageIngCinnamon":true,"beveragePrice":0.99,"beverageQuantity":83,"beverageHidden":true}'
     */
    @PutMapping(value = "/beverages", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<Integer> update(@RequestBody Beverage beverage) {
        LOGGER.debug("update({beverage}), beverage");
        Integer res = beverageService.updateBeverage(beverage);
        return res > 0
                ? new ResponseEntity<>(res, HttpStatus.OK)
                : new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    };

    /**
     * Delete selected beverage
     * curl -X DELETE localhost:8080/beverages/1
     */
    @DeleteMapping(value = "/beverages/{id}", produces = {"application/json"})
    ResponseEntity<Integer> delete(@PathVariable Integer id) {
        LOGGER.debug("delete({id})", id);
        Integer beverageId = beverageService.deleteBeverage(id);
        return beverageId > 0
                ? new ResponseEntity<>(beverageId, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    };
}
