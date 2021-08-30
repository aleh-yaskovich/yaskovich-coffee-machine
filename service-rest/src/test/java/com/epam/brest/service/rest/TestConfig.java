package com.epam.brest.service.rest;

import com.epam.brest.service.BeverageService;
import com.epam.brest.service.ClientService;
import com.epam.brest.service.IngredientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    IngredientService ingredientService() {
        return new IngredientServiceRest(restTemplate());
    }

    @Bean
    BeverageService beverageService() {
        return new BeverageServiceRest(restTemplate());
    }

    @Bean
    ClientService clientService() {
        return new ClientServiceRest(restTemplate());
    }
}
