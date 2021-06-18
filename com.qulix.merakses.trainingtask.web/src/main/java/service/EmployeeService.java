package service;

import entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(String firstName, String surName, String patronymic, String position);

    List<Employee> getAllEmployees();

    Employee getEmployee(long employeeId);

    Employee updateEmployee(long employeeId, String firstName, String surName, String patronymic, String position);

    long deleteEmployee(long employeeId);
}
