package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.model.Ingredient;
import com.goit.homeworks.restaurant.dao.IngredientDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by SeVlad on 29.10.2016.
 */
public class JdbcIngredientDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM INGREDIENTS";
    private IngredientDao dao;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        dao = new JdbcIngredientDao(db);
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void create() throws Exception {
        Ingredient testIngredient = new Ingredient(3, "Kukuruza", 100);
        Ingredient resultIngredient = dao.create(testIngredient);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM INGREDIENTS WHERE ID=3");
        assertThat(rows, hasSize(1));
        assertThat(((Integer) rows.get(0).get("ID")), equalTo(testIngredient.getId()));
        assertThat(((String) rows.get(0).get("NAME")).trim(), equalTo(testIngredient.getName()));
        assertThat(((Integer) rows.get(0).get("AMOUNT")), equalTo(testIngredient.getAmount()));

        assertThat(resultIngredient, equalTo(testIngredient));
        assertThat(resultIngredient.getId(), equalTo(3));
    }

    @Test
    public void removeFakeIngredient(){
        Ingredient fakeIngredient = new Ingredient(3, "Kukuruza", 100);
        assertThat(dao.remove(fakeIngredient), equalTo(0));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(2));
    }
    @Test
    public void removeTrueIngredient(){
        Ingredient ingredient = new Ingredient(2, "Tomato", 100);
        assertThat(dao.remove(ingredient), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(1));
        rows = jdbcTemplate.queryForList("SELECT * FROM IngredientS WHERE ID=2");
        assertThat(rows, hasSize(0));

    }

    @Test
    public void testUpdateIngredient(){
        Ingredient testIngredient = new Ingredient(2, "TomatoSuper", 100);
        assertThat(dao.update(testIngredient), equalTo(1));

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM IngredientS WHERE ID=2");
        assertThat(((String) rows.get(0).get("NAME")).trim(), equalTo(testIngredient.getName()));
        assertThat(((Integer) rows.get(0).get("AMOUNT")), equalTo(testIngredient.getAmount()));
    }

    @Test
    public void testUpdateFakeEmployee(){
        Ingredient fakeIngredient = new Ingredient(5, "TomatoSuper", 100);
        assertThat(dao.update(fakeIngredient), equalTo(0));
    }

    @Test
    public  void getAllIngredientsTest(){
        List<Ingredient> ingredients = dao.getAll();
        assertThat(ingredients, hasSize(2));
        Ingredient testedIngredient = ingredients.get(0);
        assertThat(testedIngredient.getId(), equalTo(1));
        assertThat(testedIngredient.getName(), equalTo("Potato"));
        assertThat(testedIngredient.getAmount(), equalTo(2));
        testedIngredient = ingredients.get(1);
        assertThat(testedIngredient.getId(), equalTo(2));
        assertThat(testedIngredient.getName(), equalTo("Tomato"));
        assertThat(testedIngredient.getAmount(), equalTo(3));
    }

    @Test
    public void testFindFakeIngredientByID(){
        Ingredient ingredient = dao.findIngredientById(4);
        assertThat(ingredient, equalTo(new Ingredient()));
    }

    @Test
    public void testFindFakeIngredientByIllegalID(){
        Ingredient ingredient = dao.findIngredientById(-4);
        assertThat(ingredient, equalTo(new Ingredient()));
    }

    @Test
    public void testFindIngredientByID(){
        Ingredient ingredient = dao.findIngredientById(2);
        Ingredient trueIngredient = new Ingredient(2, "Tomato", 3);
        assertThat(ingredient, equalTo(trueIngredient));
    }

    @Test
    public void testFindIngredientByName(){
        List<Ingredient> ingredients = dao.findIngredientByName("Tom");
        Ingredient trueIngredient = new Ingredient(2, "Tomato", 3);
        assertThat(ingredients, hasSize(1));
        assertThat(ingredients.get(0), equalTo(trueIngredient));
    }
    @Test
    public void getAllEndIngredient(){
        List<Ingredient> ingredients = dao.getAllEndIngredients(2);
        Ingredient trueIngredient = new Ingredient(1, "Potato", 2);
        assertThat(ingredients, hasSize(1));
        assertThat(ingredients.get(0), equalTo(trueIngredient));
    }

}