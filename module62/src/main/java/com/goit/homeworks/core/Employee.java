package com.goit.homeworks.core;

import java.util.Date;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private Date dateBirth;
    private int idPosition;
    private int salary;

    public Employee(String firstName, String lastName, Date dateBirth, int idPosition, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.idPosition = idPosition;
        this.salary = salary;
    }

    public Employee(int id, String firstName, String lastName, Date dateBirth, int idPosition, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.idPosition = idPosition;
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

    public int getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
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

        if (idPosition != employee.idPosition) return false;
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
        result = 31 * result + idPosition;
        result = 31 * result + salary;
        return result;
    }
}
