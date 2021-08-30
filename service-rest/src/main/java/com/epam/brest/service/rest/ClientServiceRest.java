package com.epam.brest.service.rest;

import com.epam.brest.model.Beverage;
import com.epam.brest.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientServiceRest implements ClientService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientServiceRest.class);

    private RestTemplate restTemplate;

    public ClientServiceRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Integer updateIngredientQuantity(List<Beverage> selectedBeverages) {
        LOGGER.debug("updateIngredientQuantity({selectedBeverages})", selectedBeverages);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<List<Beverage>> entity = new HttpEntity<>(selectedBeverages, headers);
        ResponseEntity<Integer> result = restTemplate.exchange("http://localhost:8080/client", HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }
}
