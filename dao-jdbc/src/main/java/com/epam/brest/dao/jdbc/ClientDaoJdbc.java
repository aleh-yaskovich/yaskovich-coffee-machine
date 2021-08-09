package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.ClientDao;
import com.epam.brest.model.Beverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientDaoJdbc implements ClientDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientDaoJdbc.class);

    private JdbcTemplate jdbcTemplate;

    public ClientDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * When beverages is made,this method decreases ingredient's quantity
     */
    @Override
    public Integer updateIngredientQuantity(List<Beverage> selectedBeverages) {

        LOGGER.debug("updateIngredientQuantity()", selectedBeverages);

        int[] ingredientsQuantity = new int[9];

        for(Beverage beverage : selectedBeverages) {
            ingredientsQuantity[0] += beverage.getBeverageIngCoffee();
            ingredientsQuantity[1] += beverage.getBeverageIngMilk();
            ingredientsQuantity[2] += beverage.getBeverageIngChocolate();
            ingredientsQuantity[3] += beverage.getBeverageIngWater();
            if(beverage.isBeverageIngSugar())
                ingredientsQuantity[4] += 3;
            if(beverage.isBeverageIngSyrup())
                ingredientsQuantity[5] += 10;
            if(beverage.isBeverageIngCinnamon())
                ingredientsQuantity[6] += 2;
        }
        ingredientsQuantity[7] = selectedBeverages.size(); // for ingredient 'cup'
        ingredientsQuantity[8] = selectedBeverages.size(); // for ingredient 'teaspoon'

        String updateIngrediensQuantitySql = "";
        for(int i = 0; i < ingredientsQuantity.length; i++) {
            updateIngrediensQuantitySql += "UPDATE INGREDIENT SET INGREDIENT_QUANTITY = " +
                    "(INGREDIENT_QUANTITY - "+ingredientsQuantity[i]+") WHERE INGREDIENT_ID = "+(i + 1)+"; ";
        }

        return jdbcTemplate.update(updateIngrediensQuantitySql);
    }
}
