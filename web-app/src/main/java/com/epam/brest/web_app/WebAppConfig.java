package com.epam.brest.web_app;

import com.epam.brest.service.rest.BeverageServiceRest;
import com.epam.brest.service.rest.ClientServiceRest;
import com.epam.brest.service.rest.IngredientServiceRest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.epam.brest*")
public class WebAppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    public IngredientServiceRest ingredientService() {
        return new IngredientServiceRest(restTemplate());
    }

    @Bean
    public BeverageServiceRest beverageService() {
        return new BeverageServiceRest(restTemplate());
    }

    @Bean
    public ClientServiceRest clientService() {
        return new ClientServiceRest(restTemplate());
    }

}
