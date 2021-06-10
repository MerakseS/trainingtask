package service.impl;

import entity.Employee;
import repository.EmployeeRepository;
import repository.RepositoryProvider;
import service.EmployeeService;
import service.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class DefaultEmployeeService implements EmployeeService {
    private static final EmployeeRepository employeeRepository = RepositoryProvider.getEmployeeRepository();

    @Override
    public Employee createEmployee(String firstName, String surName, String patronymic) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Employee getEmployee(long id) throws SQLException {
        Employee employee = employeeRepository.getEmployeeById(id);
        if (employee == null) {
            throw new ServiceException("Пользователь с id " + id + " не сущесвует!");
        }

        return employee;
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
