package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Position;
import com.goit.homeworks.restaurant.dao.EmployeeDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by SeVlad on 25.10.2016.
 */
@ContextConfiguration(locations = "classpath:test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcEmployeeDaoTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EmployeeDao employeeDao;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM EMPLOYEE";

    @Test
    public void test1() {
        employeeDao.create(new Employee("Seriy","Suchok", new Date(1996,12,5), new Position(1, "MainBoss"), 10000));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        Assert.assertEquals(rows.size(), 1);
        System.out.println(rows.toString());
    }

}