package service.impl;

import entity.Employee;
import org.apache.log4j.Logger;
import repository.EmployeeRepository;
import repository.RepositoryException;
import repository.RepositoryProvider;
import service.EmployeeService;
import service.ServiceException;

import java.util.List;

public class DefaultEmployeeService implements EmployeeService {
    private static final Logger log = Logger.getLogger(DefaultEmployeeService.class);
    private static final EmployeeRepository employeeRepository = RepositoryProvider.getEmployeeRepository();

    @Override
    public Employee createEmployee(String firstName, String surName, String patronymic) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Getting all employees.");
        try {
            return employeeRepository.getAllEmployees();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Employee getEmployee(long id) {
        log.info("Getting employee with id " + id);
        try {
            Employee employee = employeeRepository.getEmployeeById(id);
            if (employee == null) {
                log.error("Employee with id " + id + " doesn't exist.");
                throw new ServiceException("Пользователь с id " + id + " не сущесвует!");
            }

            return employee;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
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
