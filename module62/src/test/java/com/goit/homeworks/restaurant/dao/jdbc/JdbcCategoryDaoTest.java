package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Category;
import com.goit.homeworks.restaurant.core.Category;
import com.goit.homeworks.restaurant.dao.CategoryDao;
import com.goit.homeworks.restaurant.dao.CategoryDao;
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
import static org.junit.Assert.*;

/**
 * Created by SeVlad on 29.10.2016.
 */
public class JdbcCategoryDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM CATEGORY";
    private CategoryDao dao;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        dao = new JdbcCategoryDao(db);
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void create() throws Exception {
        Category testCategory = new Category("Deserts");
        Category resultCategory = dao.create(testCategory);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM CATEGORY WHERE ID=2");
        assertThat(rows, hasSize(1));
        assertThat(((Integer) rows.get(0).get("ID")), equalTo(testCategory.getId()));
        assertThat(((String) rows.get(0).get("NAME")).trim(), equalTo(testCategory.getName()));

        assertThat(resultCategory, equalTo(testCategory));
        assertThat(resultCategory.getId(), equalTo(2));
    }

    @Test
    public void removeFakeCategory(){
        Category fakeCategory = new Category(4, "Umbrella");
        assertThat(dao.remove(fakeCategory), equalTo(0));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(1));
    }
    @Test
    public void removeTrueCategory(){
        Category Category = new Category(1, "SOUPS");
        assertThat(dao.remove(Category), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(0));
        rows = jdbcTemplate.queryForList("SELECT * FROM CATEGORY WHERE ID=1");
        assertThat(rows, hasSize(0));
    }

    @Test
    public void testUpdateCategory(){
        Category testCategory = new Category(1, "Super Big Boss");
        assertThat(dao.update(testCategory), equalTo(1));

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM CATEGORY WHERE ID=1");
        assertThat(((String) rows.get(0).get("NAME")).trim(), equalTo(testCategory.getName()));
    }

    @Test
    public void testUpdateFakeEmployee(){
        Category fakeCategory = new Category(5, "Super Big Boss");
        assertThat(dao.update(fakeCategory), equalTo(0));
    }

    @Test
    public  void getAllCategorysTest(){
        List<Category> Categorys = dao.getAll();
        assertThat(Categorys, hasSize(1));
        Category testedCategory = Categorys.get(0);
        assertThat(testedCategory.getId(), equalTo(1));
        assertThat(testedCategory.getName(), equalTo("SOUPS"));
    }

    @Test
    public void testFindFakeCategoryByID(){
        Category Category = dao.findCategoryById(4);
        assertThat(Category, equalTo(new Category()));
    }

    @Test
    public void testFindFakeCategoryByIllegalID(){
        Category Category = dao.findCategoryById(-4);
        assertThat(Category, equalTo(new Category()));
    }

    @Test
    public void testFindCategoryByID(){
        Category Category = dao.findCategoryById(1);
        Category trueCategory = new Category(1, "SOUPS");
        assertThat(Category, equalTo(trueCategory));
    }

}