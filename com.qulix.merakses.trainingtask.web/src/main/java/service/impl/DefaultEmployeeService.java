package service.impl;

import entity.Employee;
import repository.EmployeeRepository;
import repository.RepositoryProvider;
import service.EmployeeService;

import java.util.List;

public class DefaultEmployeeService implements EmployeeService {
    private static final EmployeeRepository employeeRepository = RepositoryProvider.getEmployeeRepository();

    @Override
    public Employee createEmployee(String firstName, String surName, String patronymic) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Employee getEmployee(long id) {
        return null;
    }

    @Override
    public Employee updateEmployee(long id, String firstName, String surName, String patronymic) {
        return null;
    }

    @Override
    public long deleteEmployee(long id) {
        return 0;
    }
}
