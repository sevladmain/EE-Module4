package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.model.Position;
import com.goit.homeworks.restaurant.dao.PositionDao;
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
 * Created by SeVlad on 27.10.2016.
 */
public class JdbcPositionDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM POSITIONS";
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
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM POSITIONS WHERE ID=4");
        assertThat(rows, hasSize(1));
        assertThat(((Integer) rows.get(0).get("ID")), equalTo(testPosition.getId()));
        assertThat(((String) rows.get(0).get("POSITION")).trim(), equalTo(testPosition.getPosition()));

        assertThat(resultPosition, equalTo(testPosition));
        assertThat(resultPosition.getId(), equalTo(4));
    }

    @Test
    public void removeFakePosition(){
        Position fakePosition = new Position(4, "Umbrella");
        assertThat(dao.remove(fakePosition), equalTo(0));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(3));
    }
    @Test
    public void removeTruePosition(){
        Position position = new Position(2, "NEWBIE");
        assertThat(dao.remove(position), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(2));
        rows = jdbcTemplate.queryForList("SELECT * FROM POSITIONS WHERE ID=2");
        assertThat(rows, hasSize(0));

    }

    @Test
    public void testUpdatePosition(){
        Position testPosition = new Position(2, "Super Big Boss");
        assertThat(dao.update(testPosition), equalTo(1));

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM POSITIONS WHERE ID=2");
        assertThat(((String) rows.get(0).get("POSITION")).trim(), equalTo(testPosition.getPosition()));
    }

    @Test
    public void testUpdateFakeEmployee(){
        Position fakePosition = new Position(5, "Super Big Boss");
        assertThat(dao.update(fakePosition), equalTo(0));
    }

    @Test
    public  void getAllPositionsTest(){
        List<Position> positions = dao.getAll();
        assertThat(positions, hasSize(3));
        Position testedPosition = positions.get(0);
        assertThat(testedPosition.getId(), equalTo(1));
        assertThat(testedPosition.getPosition(), equalTo("BIG_BOSS"));
        testedPosition = positions.get(1);
        assertThat(testedPosition.getId(), equalTo(2));
        assertThat(testedPosition.getPosition(), equalTo("NEWBIE"));
        testedPosition = positions.get(2);
        assertThat(testedPosition.getId(), equalTo(3));
        assertThat(testedPosition.getPosition(), equalTo("MANAGER"));
    }

    @Test
    public void testFindFakePositionByID(){
        Position position = dao.findPositionById(4);
        assertThat(position, equalTo(new Position()));
    }

    @Test
    public void testFindFakePositionByIllegalID(){
        Position position = dao.findPositionById(-4);
        assertThat(position, equalTo(new Position()));
    }

    @Test
    public void testFindPositionByID(){
        Position position = dao.findPositionById(2);
        Position truePosition = new Position(2, "NEWBIE");
        assertThat(position, equalTo(truePosition));
    }

}