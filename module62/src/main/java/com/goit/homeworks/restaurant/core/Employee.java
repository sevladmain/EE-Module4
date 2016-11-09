package com.goit.homeworks.restaurant.core;

import java.sql.Date;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private Date dateBirth;
    private int positionId;
    private int salary;

    public Employee() {
        this(0,"","", Date.valueOf("1900-01-01"), 0, 0);
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee(String firstName, String lastName, Date dateBirth, int positionId, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.positionId = positionId;
        this.salary = salary;
    }

    public Employee(int id, String firstName, String lastName, Date dateBirth, int positionId, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.positionId = positionId;
        this.salary = salary;
    }

    public boolean isNew(){ return id == 0; }

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

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
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

        if (id != employee.id) return false;
        if (positionId != employee.positionId) return false;
        if (salary != employee.salary) return false;
        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (lastName != null ? !lastName.equals(employee.lastName) : employee.lastName != null) return false;
        return dateBirth != null ? dateBirth.equals(employee.dateBirth) : employee.dateBirth == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dateBirth != null ? dateBirth.hashCode() : 0);
        result = 31 * result + positionId;
        result = 31 * result + salary;
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateBirth=" + dateBirth +
                ", positionId=" + positionId +
                ", salary=" + salary +
                '}';
    }
}
