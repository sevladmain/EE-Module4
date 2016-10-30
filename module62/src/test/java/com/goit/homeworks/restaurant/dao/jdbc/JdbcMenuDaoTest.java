package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.*;
import com.goit.homeworks.restaurant.dao.MenuDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class JdbcMenuDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM MENU";
    private MenuDao dao;
    private Menu existingMenu1;
    private Menu existingMenu2;
    private Menu newMenu;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        JdbcCategoryDao categoryDao = new JdbcCategoryDao(db);
        JdbcIngredientDao ingredientDao = new JdbcIngredientDao(db);
        JdbcDishDao dishDao = new JdbcDishDao(db, categoryDao, ingredientDao);
        dao = new JdbcMenuDao(db, dishDao);

        Dish existDish;
        existDish = new Dish();
        existDish .setId(1);
        existDish .setCategory(new Category(1, "SOUPS"));
        existDish .setPrice(100);
        existDish .setWeight(250);
        existDish.setName("Chicken");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "Potato", 2));
        ingredients.add(new Ingredient(2, "Tomato", 3));
        existDish.setIngredientList(ingredients);

        List<Dish> dishes = new ArrayList<>();
        dishes.add(existDish);
        existingMenu1 = new Menu(1, "Mega-Menu",dishes);
        existingMenu2 = new Menu(2, "Empty-Menu", new ArrayList<>());

        newMenu = new Menu(3, "Ok-menu", dishes);
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void createMenu() throws Exception {
        Menu resultMenu = dao.create(newMenu);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM MENU WHERE ID=3");
        assertThat("Row not inserted or not exists", rows, hasSize(1));

        assertThat("ID is not equal", ((Integer) rows.get(0).get("ID")), equalTo(newMenu.getId()));
        assertThat("NAME DISH is not equal", ((String) rows.get(0).get("NAME")).trim(), equalTo(newMenu.getName()));

        rows = jdbcTemplate.queryForList("SELECT * FROM MENULIST WHERE ID_MENU=3");
        assertThat(rows, hasSize(1));

        assertThat("INGREDIENTLIST is not inserted", ((Integer) rows.get(0).get("ID_DISH")), equalTo(newMenu.getDishes().get(0).getId()));

        assertThat("Result dish and test dish not equal", resultMenu, equalTo(newMenu));
        assertThat("Result dish has wrong ID", resultMenu.getId(), equalTo(3));
    }


    @Test
    public void removeTrueMenu(){
        assertThat(dao.remove(existingMenu1), equalTo(2));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(1));
        rows = jdbcTemplate.queryForList("SELECT * FROM MENULIST WHERE ID_MENU=" + existingMenu1.getId());
        assertThat(rows, hasSize(0));
    }

    @Test
    public void testUpdateMenu(){
        newMenu.setId(2);
        assertThat(dao.update(newMenu), equalTo(1));

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM MENU WHERE ID=2");
        assertThat("Row not inserted or not exists", rows, hasSize(1));

        assertThat("ID is not equal", ((Integer) rows.get(0).get("ID")), equalTo(newMenu.getId()));
        assertThat("NAME DISH is not equal", ((String) rows.get(0).get("NAME")).trim(), equalTo(newMenu.getName()));

        rows = jdbcTemplate.queryForList("SELECT * FROM MENULIST WHERE ID_MENU=2");
        assertThat(rows, hasSize(1));

        assertThat("INGREDIENTLIST is not inserted", ((Integer) rows.get(0).get("ID_DISH")), equalTo(newMenu.getDishes().get(0).getId()));
    }

    @Test
    public  void getAllMenusTest(){
        List<Menu> menus = dao.getAll();
        assertThat(menus, hasSize(2));
        assertThat(menus.get(0), equalTo(existingMenu1));
        assertThat(menus.get(1), equalTo(existingMenu2));
    }

    @Test
    public void testFindFakeMenuByID(){
        Menu menu = dao.findMenuById(3);
        assertThat(menu, equalTo(new Menu()));
    }

    @Test
    public void testFindFakeMenuByIllegalID(){
        Menu menu = dao.findMenuById(-3);
        assertThat(menu, equalTo(new Menu()));
    }

    @Test
    public void testFindMenuByID(){
        Menu menu = dao.findMenuById(1);
        assertThat(menu, equalTo(existingMenu1));
    }

    @Test
    public void findDishByName() throws Exception {
        List<Menu> menus = dao.findMenuByName("pty");
        assertThat(menus, hasSize(1));
        assertThat(menus.get(0), equalTo(existingMenu2));
    }
}