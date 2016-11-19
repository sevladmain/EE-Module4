package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.model.Employee;
import com.goit.homeworks.restaurant.model.Position;
import com.goit.homeworks.restaurant.dao.EmployeeDao;
import com.goit.homeworks.restaurant.dao.PositionDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SeVlad on 06.11.2016.
 */
@Service
public class EmployeeService {
    private EmployeeDao employeeDao;
    private PositionDao positionDao;

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    @Transactional
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Transactional
    public PositionDao getPositionDao() {
        return positionDao;
    }

    @Transactional
    public void setPositionDao(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    @Transactional
    public Map<Employee, Position> getAllEmployees() {
        Map<Employee, Position> result = new HashMap<>();
        List<Employee> employees = employeeDao.getAll();
        for (Employee employee :
                employees) {
            result.put(employee, positionDao.findPositionById(employee.getPositionId()));
        }
        return result;
    }

    @Transactional
    public Map<Employee, Position> findEmployeeByName(String name) {
        Map<Employee, Position> result = new HashMap<>();
        List<Employee> employees = employeeDao.findEmployeeByName(name);
        for (Employee employee :
                employees) {
            result.put(employee, positionDao.findPositionById(employee.getPositionId()));
        }
        return result;
    }

    @Transactional
    public Employee getEmployeeById(int id) {
        return employeeDao.findEmployeeById(id);
    }

    @Transactional
    public int deleteEmployee(Employee employee) {
        return employeeDao.remove(employee);
    }

    @Transactional
    public Employee addEmployee(Employee employee) {
        return employeeDao.create(employee);
    }

    @Transactional
    public List<Position> getAllPositions() {
        return positionDao.getAll();
    }

    @Transactional
    public int updateEmployee(Employee employee) {
        return employeeDao.update(employee);
    }
}
