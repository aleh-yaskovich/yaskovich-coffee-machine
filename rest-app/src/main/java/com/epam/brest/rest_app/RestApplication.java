package com.epam.brest.rest_app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@SpringBootApplication
@ComponentScan("com.epam.brest*")
@OpenAPIDefinition(
        info = @Info(
                title = "Coffee Machine",
                description = "Coffee Machine rest-application",
                termsOfService = "https://github.com/aleh-yaskovich/yaskovich-coffee-machine",
                contact = @Contact(
                        name = "Aleh Yaskovich",
                        url = "epam.com",
                        email = "aleh.yaskovich@epam.com"
                ),
                license = @License(
                        name = "",
                        url =""
                ),
                version = "1.0"
        )
)
public class RestApplication {

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
        SpringApplication.run(RestApplication.class, args);
    }
}
