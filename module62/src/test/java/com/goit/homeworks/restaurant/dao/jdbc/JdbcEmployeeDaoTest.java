package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Position;
import com.goit.homeworks.restaurant.dao.EmployeeDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
 * Created by SeVlad on 25.10.2016.
 */
//@ContextConfiguration(locations = "classpath:test.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcEmployeeDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM EMPLOYEE";
    private EmployeeDao employeeDao;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        employeeDao = new JdbcEmployeeDao(db);
    }

    @Test
    public void test2() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        System.out.println(rows.toString());
    }
    @Test
    public  void getAllEmployeesTest(){
        List<Employee> employees = employeeDao.getAll();
        Position position = new Position(2, "NEWBIE");

        Employee employee = new Employee(1, "Mary", "Ivanova", Date.valueOf("1998-08-12"), position, 1000);

        assertThat(employees, hasSize(1));
        Employee testedEmployee = employees.get(0);
        assertThat(testedEmployee.getFirstName(), equalTo(employee.getFirstName()));
        assertThat(testedEmployee.getLastName(), equalTo(employee.getLastName()));
        assertThat(testedEmployee.getSalary(), equalTo(employee.getSalary()));
        assertThat(testedEmployee.getPosition().getId(), equalTo(employee.getPosition().getId()));
        assertThat(testedEmployee.getDateBirth(), equalTo(employee.getDateBirth()));
    }
    @Test
    public void createNewEmployee(){
        Employee testEmployee = new Employee("Seriy","Suchok", Date.valueOf("1996-12-05"), new Position(1, "MainBoss"), 10000);
        Employee resultEmployee = employeeDao.create(testEmployee);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM EMPLOYEE WHERE ID=2");
        assertThat(rows, hasSize(1));
        assertThat(((String) rows.get(0).get("FIRST_NAME")).trim(), equalTo(testEmployee.getFirstName()));
        assertThat(((String) rows.get(0).get("LAST_NAME")).trim(), equalTo(testEmployee.getLastName()));
        assertThat((Date) rows.get(0).get("DATE_BIRTH"), equalTo(testEmployee.getDateBirth()));
        assertThat((Integer) rows.get(0).get("ID_POSITION"), equalTo(testEmployee.getPosition().getId()));
        assertThat((Integer) rows.get(0).get("SALARY"), equalTo(testEmployee.getSalary()));

        assertThat(resultEmployee.getId(), equalTo(2));
        assertThat(resultEmployee.getPosition(), equalTo(testEmployee.getPosition()));
        assertThat(resultEmployee.getDateBirth(), equalTo(testEmployee.getDateBirth()));
        assertThat(resultEmployee.getSalary(), equalTo(testEmployee.getSalary()));
        assertThat(resultEmployee.getFirstName(), equalTo(testEmployee.getFirstName()));
        assertThat(resultEmployee.getLastName(), equalTo(testEmployee.getLastName()));
    }

    @Ignore
    @Test(expected = Exception.class)
    public void createEmployeeWithExistingID(){
        employeeDao.create(new Employee(1, "Seriy","Suchok", Date.valueOf("1996-12-05"), new Position(1, "MainBoss"), 10000));
//        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
    }

    @After
    public void tearDown() {
        db.shutdown();
    }
}