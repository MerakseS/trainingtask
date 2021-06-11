package service;

import entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(String firstName, String surName, String patronymic);

    List<Employee> getAllEmployees();

    Employee getEmployee(long id);

    Employee updateEmployee(long id, String firstName, String surName, String patronymic);

    long deleteEmployee(long id);
}
