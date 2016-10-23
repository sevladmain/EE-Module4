package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.core.Employee;

import java.util.List;

/**
 * Created by SeVlad on 23.10.2016.
 */
public interface EmployeeDAO {
    boolean addEmployee(Employee employees);
    boolean removeEmployee(Employee employees);
    List<Employee> findEmployeesByName(String name);
    List<Employee> getAllEmployees();
}
