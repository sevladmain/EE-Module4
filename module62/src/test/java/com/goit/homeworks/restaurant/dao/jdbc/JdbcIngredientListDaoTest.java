package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.dao.IngredientListDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by SeVlad on 01.11.2016.
 */
public class JdbcIngredientListDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM INGREDIENTLIST";
    private IngredientListDao dao;
    private int dishId;
    private int existingIngrId1;
    private int existingIngrId2;
    private int newIngrId;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        dao = new JdbcIngredientListDao(db);
        dishId = 1;
        existingIngrId1 = 1;
        existingIngrId2 = 2;
        newIngrId = 3;
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void addIngredientToDishTest() {
        assertThat(dao.addIngredientToDish(newIngrId, dishId, 10 * newIngrId + dishId), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM INGREDIENTLIST WHERE ID_DISH=" + dishId + " AND ID_INGREDIENT=" + newIngrId);
        assertThat("Row not inserted or not exists", rows, hasSize(1));

        assertThat(((Integer) rows.get(0).get("ID_DISH")), equalTo(dishId));
        assertThat(((Integer) rows.get(0).get("ID_INGREDIENT")), equalTo(newIngrId));
        assertThat(((Integer) rows.get(0).get("USED_AMOUNT")), equalTo(10 * newIngrId + dishId));

    }

    @Test
    public void removeIngredientFromDishTest() {
        assertThat(dao.removeIngredientFromDish(existingIngrId1, dishId), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM INGREDIENTLIST WHERE ID_DISH=" + dishId + " AND ID_INGREDIENT=" + existingIngrId1);
        assertThat("Row not inserted or not exists", rows, hasSize(0));
    }

    @Test
    public void isIngredientFromDishTest() {
        assertThat(dao.isIngredientFromDish(existingIngrId1, dishId), is(true));
        assertThat(dao.isIngredientFromDish(existingIngrId2, dishId), is(true));
        assertThat(dao.isIngredientFromDish(newIngrId, dishId), is(false));
    }

    @Test
    public void getAllIngredientsIdsTest() {
        List<Integer> list = dao.getAllIngredientsIds(dishId);
        assertThat(list, hasSize(2));
        assertThat(list, contains(existingIngrId1, existingIngrId2));
    }

    @Test
    public void getUsedAmountIngredient() {
        assertThat(dao.getUsedAmountOfDishIngredient(existingIngrId1, dishId), equalTo(10* existingIngrId1 + dishId));
    }

}