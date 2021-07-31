package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.BeverageDao;
import com.epam.brest.model.Beverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BeverageDaoJdbc implements BeverageDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(BeverageDaoJdbc.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${beverage.findAllBeveragesSql}")
    private String findAllBeveragesSql;

    @Value("${beverage.findVisibleBeveragesSql}")
    private String findVisibleBeveragesSql;

    @Value("${beverage.findBeverageByIdSql}")
    private String findBeverageByIdSql;

    @Value("${beverage.createBeverageSql}")
    private String createBeverageSql;

    @Value("${beverage.updateBeverageSql}")
    private String updateBeverageSql;

    @Value("${beverage.deleteBeverageSql}")
    private String deleteBeverageSql;

    @Value("${beverage.checkBeverageTitleSql}")
    private String checkBeverageTitleSql;

    public BeverageDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Return list with all beverages
     */
    @Override
    public List<Beverage> findAllBeverages() {
        LOGGER.debug("findAllBeverages()");
        return namedParameterJdbcTemplate.query(findAllBeveragesSql, new BeverageRowMapper());
    }

    /**
     * Return list with all visible beverages for Client
     */
    @Override
    public List<Beverage> findVisibleBeverages() {
        LOGGER.debug("findVisibleBeverages()");
        return namedParameterJdbcTemplate.query(findVisibleBeveragesSql, new BeverageRowMapper());
    }

    /**
     * Return selected beverage by ID
     */
    @Override
    public Optional<Beverage> findBeverageById(Integer beverageId) {
        LOGGER.debug("findBeverageById({})", beverageId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("BEVERAGE_ID", beverageId);
        // Note: don't use queryForObject to reduce exception handling
        List<Beverage> results = namedParameterJdbcTemplate.query(findBeverageByIdSql, sqlParameterSource, new BeverageRowMapper());
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }

    /**
     * Create new beverage
     */
    @Override
    public Integer createBeverage(Beverage beverage) {
        LOGGER.debug("createBeverage({})", beverage);
        if(!isBeverageTitleUnique(beverage)) {
            throw new IllegalArgumentException("Beverage with the same name is already been in DB");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(createBeverageSql, createSqlParameterSource(beverage), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    /**
     * Update selected beverage
     */
    @Override
    public Integer updateBeverage(Beverage beverage) {
        LOGGER.debug("updateBeverage({})", beverage);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(updateBeverageSql, createSqlParameterSource(beverage), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    /**
     * Delete selected beverage
     */
    @Override
    public Integer deleteBeverage(Integer beverageId) {
        LOGGER.debug("deleteBeverage({})", beverageId);
        return namedParameterJdbcTemplate.update(deleteBeverageSql, new MapSqlParameterSource()
                .addValue("BEVERAGE_ID", beverageId));
    }

    /**
     * Create SqlParameterSource
     */
    private SqlParameterSource createSqlParameterSource(Beverage beverage) {
        Map<String, Object> sqlParameter = new HashMap();
        sqlParameter.put("BEVERAGE_ID", beverage.getBeverageId());
        sqlParameter.put("BEVERAGE_TITLE", beverage.getBeverageTitle());
        sqlParameter.put("BEVERAGE_ING_COFFEE", beverage.getBeverageIngCoffee());
        sqlParameter.put("BEVERAGE_ING_MILK", beverage.getBeverageIngMilk());
        sqlParameter.put("BEVERAGE_ING_CHOCOLATE", beverage.getBeverageIngChocolate());
        sqlParameter.put("BEVERAGE_ING_WATER", beverage.getBeverageIngWater());
        sqlParameter.put("BEVERAGE_ING_SUGAR", beverage.isBeverageIngSugar());
        sqlParameter.put("BEVERAGE_ING_SYRUP", beverage.isBeverageIngSyrup());
        sqlParameter.put("BEVERAGE_ING_CINNAMON", beverage.isBeverageIngCinnamon());
        sqlParameter.put("BEVERAGE_HIDDEN", beverage.isBeverageHidden());
        return new MapSqlParameterSource(sqlParameter);
    }

    /**
     * Check beverage title for uniqueness
     */
    private boolean isBeverageTitleUnique(Beverage beverage) {
        LOGGER.debug("isBeverageNameUnique({})", beverage);
        return namedParameterJdbcTemplate.queryForObject(checkBeverageTitleSql,
                new MapSqlParameterSource("BEVERAGE_TITLE", beverage.getBeverageTitle()), Integer.class) == 0;
    }

    private class BeverageRowMapper implements RowMapper<Beverage> {
        @Override
        public Beverage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Beverage beverage = new Beverage();
            beverage.setBeverageId(resultSet.getInt("BEVERAGE_ID"));
            beverage.setBeverageTitle(resultSet.getString("BEVERAGE_TITLE"));
            beverage.setBeverageIngCoffee(resultSet.getInt("BEVERAGE_ING_COFFEE"));
            beverage.setBeverageIngMilk(resultSet.getInt("BEVERAGE_ING_MILK"));
            beverage.setBeverageIngChocolate(resultSet.getInt("BEVERAGE_ING_CHOCOLATE"));
            beverage.setBeverageIngWater(resultSet.getInt("BEVERAGE_ING_WATER"));
            beverage.setBeverageIngSugar(resultSet.getBoolean("BEVERAGE_ING_SUGAR"));
            beverage.setBeverageIngSyrup(resultSet.getBoolean("BEVERAGE_ING_SYRUP"));
            beverage.setBeverageIngCinnamon(resultSet.getBoolean("BEVERAGE_ING_CINNAMON"));
            beverage.setBeverageHidden(resultSet.getBoolean("BEVERAGE_HIDDEN"));
            beverage.setBeveragePrice(resultSet.getDouble("BEVERAGE_PRICE"));
            return beverage;
        }
    }
}
