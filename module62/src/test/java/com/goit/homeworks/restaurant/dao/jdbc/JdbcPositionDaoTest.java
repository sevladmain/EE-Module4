package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Position;
import com.goit.homeworks.restaurant.dao.PositionDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by SeVlad on 27.10.2016.
 */
public class JdbcPositionDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM POSITION";
    private PositionDao dao;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        dao = new JdbcPositionDao(db);
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void create() throws Exception {
        Position testPosition = new Position("SUPER");
        Position resultPosition = dao.create(testPosition);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM CATEGORY WHERE ID=4");
        assertThat(rows, hasSize(1));
        assertThat(((String) rows.get(0).get("ID")).trim(), equalTo(testPosition.getId()));
        assertThat(((String) rows.get(0).get("POSITION")).trim(), equalTo(testPosition.getPosition()));

        testPosition.setId(2);
        assertThat(resultPosition, equalTo(testPosition));

    }

    @Test
    public void remove() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void findPositionById() throws Exception {

    }

}