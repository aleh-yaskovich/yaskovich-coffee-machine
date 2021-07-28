package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.IngredientDao;
import com.epam.brest.model.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IngredientDaoJdbc implements IngredientDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(IngredientDaoJdbc.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String findAllIngredientsSql = "SELECT * FROM INGREDIENT";
    private String findIngredientByIdSql = "SELECT * FROM INGREDIENT AS I WHERE I.INGREDIENT_ID = :INGREDIENT_ID";
    private String updateIngredientSql = "UPDATE INGREDIENT SET INGREDIENT_TITLE = :INGREDIENT_TITLE, " +
            "INGREDIENT_QUANTITY = :INGREDIENT_QUANTITY, INGREDIENT_EXPIRATION_DATE = :INGREDIENT_EXPIRATION_DATE, " +
            "INGREDIENT_PRICE = :INGREDIENT_PRICE, INGREDIENT_REQUIRED = :INGREDIENT_REQUIRED " +
            "WHERE INGREDIENT_ID = :INGREDIENT_ID";

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
