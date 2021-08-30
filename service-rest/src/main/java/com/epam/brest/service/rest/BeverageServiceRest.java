package com.epam.brest.service.rest;

import com.epam.brest.model.Beverage;
import com.epam.brest.service.BeverageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BeverageServiceRest implements BeverageService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BeverageServiceRest.class);

    private String url = "http://localhost:8080/beverages";
    private RestTemplate restTemplate;

    public BeverageServiceRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Beverage> findAllBeverages() {
        LOGGER.debug("findAllBeverages()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Beverage>) responseEntity.getBody();
    }

    @Override
    public List<Beverage> findVisibleBeverages() {
        LOGGER.debug("findVisibleBeverages()");
        ResponseEntity responseEntity = restTemplate.getForEntity("http://localhost:8080/client", List.class);
        return (List<Beverage>) responseEntity.getBody();
    }

    @Override
    public Beverage findBeverageById(Integer beverageId) {
        LOGGER.debug("findBeverageById({beverageId})", beverageId);
        ResponseEntity<Beverage> responseEntity =
                restTemplate.getForEntity(url + "/" + beverageId, Beverage.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer createBeverage(Beverage beverage) {
        LOGGER.debug("createBeverage({beverage})", beverage);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, beverage, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer updateBeverage(Beverage beverage) {
        LOGGER.debug("updateBeverage({beverage})", beverage);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Beverage> entity = new HttpEntity<>(beverage, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer deleteBeverage(Integer beverageId) {
        LOGGER.debug("deleteBeverage({beverageId})", beverageId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Beverage> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + beverageId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }
}
