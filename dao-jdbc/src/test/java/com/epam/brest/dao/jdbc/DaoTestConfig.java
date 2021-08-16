package com.epam.brest.dao.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class DaoTestConfig {

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
    public DataSource h2DataSource() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("create-test-db.sql", "init-test-db.sql")
                .build();
        return db;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(h2DataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(h2DataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(h2DataSource());
    }

    @Bean
    public BeverageDaoJdbc beverageDaoJdbc() {
        return new BeverageDaoJdbc(namedParameterJdbcTemplate());
    }

    @Bean
    public IngredientDaoJdbc ingredientDaoJdbc() {
        return new IngredientDaoJdbc(namedParameterJdbcTemplate());
    }

    @Bean
    public ClientDaoJdbc clientDaoJdbc() {
        return new ClientDaoJdbc(jdbcTemplate());
    }
}
