package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Position;
import com.goit.homeworks.restaurant.dao.EmployeeDao;
import com.goit.homeworks.restaurant.dao.PositionDao;
import org.springframework.stereotype.Service;

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

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public PositionDao getPositionDao() {
        return positionDao;
    }

    public void setPositionDao(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    public Map<Employee, Position> getAllEmployees(){
        Map<Employee, Position> result = new HashMap<>();
        List<Employee> employees = employeeDao.getAll();
        for (Employee employee :
                employees) {
            result.put(employee, positionDao.findPositionById(employee.getPositionId()));
        }
        return result;
    }
    public Employee getEmployeeById(int id){
        return employeeDao.findEmployeeById(id);
    }
    public int deleteEmployee(Employee employee){
        return employeeDao.remove(employee);
    }

    public Employee addEmployee(Employee employee){
        return employeeDao.create(employee);
    }
    public  List<Position> getAllPositions(){
        return positionDao.getAll();
    }
    public int updateEmployee(Employee employee){ return employeeDao.update(employee);}
}
