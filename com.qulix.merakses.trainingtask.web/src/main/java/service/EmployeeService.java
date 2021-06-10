package service;

import entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {

    Employee createEmployee(String firstName, String surName, String patronymic);

    List<Employee> getAllEmployees() throws SQLException;

    Employee getEmployee(long id) throws SQLException;

    Employee updateEmployee(long id, String firstName, String surName, String patronymic);

    long deleteEmployee(long id);
}
