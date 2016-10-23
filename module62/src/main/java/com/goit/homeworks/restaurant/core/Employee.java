package com.goit.homeworks.restaurant.core;

import java.util.Date;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private Date dateBirth;
    private Position position;
    private int salary;

    public Employee() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee(String firstName, String lastName, Date dateBirth, Position position, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.position = position;
        this.salary = salary;
    }

    public Employee(int id, String firstName, String lastName, Date dateBirth, Position position, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.position = position;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!position.equals(employee.position)) return false;
        if (salary != employee.salary) return false;
        if (!firstName.equals(employee.firstName)) return false;
        if (!lastName.equals(employee.lastName)) return false;
        return dateBirth.equals(employee.dateBirth);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + dateBirth.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + salary;
        return result;
    }
}
