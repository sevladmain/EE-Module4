package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.dao.MenuListDao;
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
 * Created by SeVlad on 02.11.2016.
 */
public class JdbcMenuListDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM MENULIST";
    private MenuListDao dao;
    private int menuId;
    private int existingDishId1;
    private int existingDishId2;
    private int newDishId;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        dao = new JdbcMenuListDao(db);
        menuId = 1;
        existingDishId1 = 2;
        existingDishId2 = 3;
        newDishId = 4;
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void addDishToMenuTest() {
        assertThat(dao.addDishToMenu(newDishId, menuId), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM MENULIST WHERE ID_MENU=" + menuId + " AND ID_DISH=" + newDishId);
        assertThat("Row not inserted or not exists", rows, hasSize(1));

        assertThat(((Integer) rows.get(0).get("ID_MENU")), equalTo(menuId));
        assertThat(((Integer) rows.get(0).get("ID_DISH")), equalTo(newDishId));
    }

    @Test
    public void removeDishFromMenuTest() {
        assertThat(dao.removeDishFromMenu(existingDishId1, menuId), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM MENULIST WHERE ID_MENU=" + menuId + " AND ID_DISH=" + existingDishId1);
        assertThat("Row not inserted or not exists", rows, hasSize(0));
    }

    @Test
    public void isDishFromMenuTest() {
        assertThat(dao.isDishFromMenu(existingDishId1, menuId), is(true));
        assertThat(dao.isDishFromMenu(existingDishId2, menuId), is(true));
        assertThat(dao.isDishFromMenu(newDishId, menuId), is(false));
    }

    @Test
    public void getAllDishesTest() {
        List<Integer> list = dao.getAllDishes(menuId);
        assertThat(list, hasSize(2));
        assertThat(list, contains(existingDishId1, existingDishId2));
    }
}