package com.epam.brest.web_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
@ComponentScan("com.epam.brest*")
public class ApplicationMvcConfig {


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholder = new PropertySourcesPlaceholderConfigurer();
        Resource[] resourceLocations = new Resource[] {
                new ClassPathResource("dao.properties"),
                new ClassPathResource("application.properties")
        };
        propertySourcesPlaceholder.setLocations(resourceLocations);
        return propertySourcesPlaceholder;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMvcConfig.class, args);
    }

}
