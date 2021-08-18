package com.epam.brest.web_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan("com.epam.brest*")
public class ApplicationMvcConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholder = new PropertySourcesPlaceholderConfigurer();
        Resource[] resourceLocations = new Resource[] {
                new ClassPathResource("dao.properties")
        };
        propertySourcesPlaceholder.setLocations(resourceLocations);
        return propertySourcesPlaceholder;
    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScripts("create-test-db.sql", "init-test-db.sql")
                .build();
        return db;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMvcConfig.class, args);
    }

}
