package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Position;
import com.goit.homeworks.restaurant.dao.EmployeeDao;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Created by SeVlad on 25.10.2016.
 */
//@ContextConfiguration(locations = "classpath:test.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcEmployeeDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM EMPLOYEE";
    private EmployeeDao dao;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        dao = new JdbcEmployeeDao(db);
    }

    @Test
    public  void getAllEmployeesTest(){
        List<Employee> employees = dao.getAll();
        Position position = new Position(2, "NEWBIE");

        Employee employee = new Employee(1, "Mary", "Ivanova", Date.valueOf("1998-08-12"), position, 1000);

        assertThat(employees, hasSize(1));
        Employee testedEmployee = employees.get(0);
        assertThat(testedEmployee.getFirstName(), equalTo(employee.getFirstName()));
        assertThat(testedEmployee.getLastName(), equalTo(employee.getLastName()));
        assertThat(testedEmployee.getSalary(), equalTo(employee.getSalary()));
        assertThat(testedEmployee.getPosition().getId(), equalTo(employee.getPosition().getId()));
        assertThat(testedEmployee.getPosition().getPosition(), equalTo(null));
        assertThat(testedEmployee.getDateBirth(), equalTo(employee.getDateBirth()));
    }
    @Test
    public void createNewEmployee(){
        Employee testEmployee = new Employee("Seriy","Suchok", Date.valueOf("1996-12-05"), new Position(1, "MainBoss"), 10000);
        Employee resultEmployee = dao.create(testEmployee);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM EMPLOYEE WHERE ID=2");
        assertThat(rows, hasSize(1));
        assertThat(((String) rows.get(0).get("FIRST_NAME")).trim(), equalTo(testEmployee.getFirstName()));
        assertThat(((String) rows.get(0).get("LAST_NAME")).trim(), equalTo(testEmployee.getLastName()));
        assertThat((Date) rows.get(0).get("DATE_BIRTH"), equalTo(testEmployee.getDateBirth()));
        assertThat((Integer) rows.get(0).get("ID_POSITION"), equalTo(testEmployee.getPosition().getId()));
        assertThat((Integer) rows.get(0).get("SALARY"), equalTo(testEmployee.getSalary()));

        assertThat(resultEmployee, equalTo(testEmployee));
        assertThat(resultEmployee.getId(), equalTo(2));
    }


    @Test
    public void createEmployeeWithExistingID(){
        Employee employee = dao.create(new Employee(1, "Seriy","Suchok", Date.valueOf("1996-12-05"), new Position(1, "MainBoss"), 10000));
//        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(employee.getId(), equalTo(2));
    }

    @Test
    public void removeFakeEmployee(){
        Employee fakeEmployee = new Employee(5, "Seriy","Suchok", Date.valueOf("1996-12-05"), new Position(1, "MainBoss"), 10000);
        assertThat(dao.remove(fakeEmployee), equalTo(0));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(1));
    }
    @Test
    public void removeTrueEmployee(){
        Employee employee = new Employee(1, "Mary", "Ivanova", Date.valueOf("1998-08-12"), new Position(2), 1000);
        assertThat(dao.remove(employee), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(0));
    }

    @Test
    public void testFindFakeEmployeeByID(){
        Employee employee = dao.findEmployeeById(3);
        assertThat(employee, equalTo(new Employee()));
    }

    @Test
    public void testFindFakeEmployeeByIllegalID(){
        Employee employee = dao.findEmployeeById(-3);
        assertThat(employee, equalTo(new Employee()));
    }

    @Test
    public void testFindEmployeeByID(){
        Employee employee = dao.findEmployeeById(1);
        Employee trueEmployee = new Employee(1, "Mary", "Ivanova", Date.valueOf("1998-08-12"), new Position(2), 1000);
        assertThat(employee, equalTo(trueEmployee));
    }

    @Test
    public void testUpdateEmployee(){
        Employee testEmployee = new Employee(1, "Sergiy", "Ivanov", Date.valueOf("2008-07-01"), new Position(3), 2000);
        assertThat(dao.update(testEmployee), equalTo(1));

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM EMPLOYEE WHERE ID=1");
        assertThat(((String) rows.get(0).get("FIRST_NAME")).trim(), equalTo(testEmployee.getFirstName()));
        assertThat(((String) rows.get(0).get("LAST_NAME")).trim(), equalTo(testEmployee.getLastName()));
        assertThat((Date) rows.get(0).get("DATE_BIRTH"), equalTo(testEmployee.getDateBirth()));
        assertThat((Integer) rows.get(0).get("ID_POSITION"), equalTo(testEmployee.getPosition().getId()));
        assertThat((Integer) rows.get(0).get("SALARY"), equalTo(testEmployee.getSalary()));
    }

    @Test
    public void testUpdateFakeEmployee(){
        Employee fakeEmployee = new Employee(2, "Mary", "Ivanova", Date.valueOf("1998-08-12"), new Position(2), 1000);
        assertThat(dao.update(fakeEmployee), equalTo(0));
    }


    @Test
    public void testFindFakeEmployeeByName(){
        List<Employee> employee = dao.findEmployeeByName("Serg");
        assertThat(employee, hasSize(0));
    }

    @Test
    public void testFindTrueEmployeeByName(){
        Employee trueEmployee = new Employee(1, "Mary", "Ivanova", Date.valueOf("1998-08-12"), new Position(2), 1000);
        List<Employee> employee = dao.findEmployeeByName("Ivan");
        assertThat(employee, hasSize(1));
        assertThat(employee.get(0), equalTo(trueEmployee));
    }

    @After
    public void tearDown() {
        db.shutdown();
    }
}