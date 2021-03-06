package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.IngredientDao;
import com.epam.brest.model.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class IngredientDaoJdbc implements IngredientDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(IngredientDaoJdbc.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Double.class);

    @Value("${ingredient.findAllIngredientsSql}")
    private String findAllIngredientsSql;

    @Value("${ingredient.findIngredientByIdSql}")
    private String findIngredientByIdSql;

    @Value("${ingredient.updateIngredientSql}")
    private String updateIngredientSql;

    public IngredientDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Return list with all ingredients
     */
    @Override
    public List<Ingredient> findAllIngredients() {
        LOGGER.debug("findAllIngredients()");
        return namedParameterJdbcTemplate.query(findAllIngredientsSql, new IngredientRowMapper());
    }

    /**
     * Return selected ingredient by ID
     */
    @Override
    public Optional<Ingredient> findIngredientById(Integer ingredientId) {
        LOGGER.debug("findIngredientById({})", ingredientId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("INGREDIENT_ID", ingredientId);
        // Note: don't use queryForObject to reduce exception handling
        List<Ingredient> results = namedParameterJdbcTemplate.query(findIngredientByIdSql, sqlParameterSource, new IngredientRowMapper());
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }

    /**
     * Update selected ingredient
     */
    @Override
    public Integer updateIngredient(Ingredient ingredient) {
        LOGGER.debug("updateIngredient({})", ingredient);
        Map sqlParameter = new HashMap();
        sqlParameter.put("INGREDIENT_ID", ingredient.getIngredientId());
        sqlParameter.put("INGREDIENT_TITLE", ingredient.getIngredientTitle());
        sqlParameter.put("INGREDIENT_QUANTITY", ingredient.getIngredientQuantity());
        sqlParameter.put("INGREDIENT_EXPIRATION_DATE", ingredient.getIngredientExpirationDate());
        sqlParameter.put("INGREDIENT_PRICE", ingredient.getIngredientPrice());
        sqlParameter.put("INGREDIENT_REQUIRED", ingredient.isIngredientRequired());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(sqlParameter);
        return namedParameterJdbcTemplate.update(updateIngredientSql, sqlParameterSource);
    }


    /**
     * Calculate prices for optional ingredients
     */
    @Override
    public List<Double> getOptionalIngredientsPrices() {
        LOGGER.debug("getOptionalIngredientsPrices()");
        List<Ingredient> ingredients = namedParameterJdbcTemplate.query(findAllIngredientsSql, new IngredientRowMapper());
        List<Double> optionalIngredientsPrices = Arrays.asList(
                roundNumberTo2(ingredients.get(4).getIngredientPrice() / 1000 * 3),
                roundNumberTo2(ingredients.get(5).getIngredientPrice() / 1000 * 10),
                roundNumberTo2(ingredients.get(6).getIngredientPrice() / 1000 * 2)
        );
        return optionalIngredientsPrices;
    }

    /**
     * Round a fractional number to two decimal places
     **/
    private double roundNumberTo2(double d) {
        double scale = Math.pow(10, 2);
        double result = Math.ceil(d * scale) / scale;
        return result;
    }

    private class IngredientRowMapper implements RowMapper<Ingredient>{
        @Override
        public Ingredient mapRow(ResultSet resultSet, int i) throws SQLException {
            Ingredient ingredient = new Ingredient();
            ingredient.setIngredientId(resultSet.getInt("INGREDIENT_ID"));
            ingredient.setIngredientTitle(resultSet.getString("INGREDIENT_TITLE"));
            ingredient.setIngredientQuantity(resultSet.getInt("INGREDIENT_QUANTITY"));
            ingredient.setIngredientExpirationDate(resultSet.getDate("INGREDIENT_EXPIRATION_DATE"));
            ingredient.setIngredientPrice(resultSet.getDouble("INGREDIENT_PRICE"));
            ingredient.setIngredientRequired(resultSet.getBoolean("INGREDIENT_REQUIRED"));
            return ingredient;
        }
    }
}
