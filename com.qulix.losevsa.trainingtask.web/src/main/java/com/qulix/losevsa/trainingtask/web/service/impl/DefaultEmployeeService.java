package com.qulix.losevsa.trainingtask.web.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.repository.EmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryException;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryProvider;
import com.qulix.losevsa.trainingtask.web.service.EmployeeService;
import com.qulix.losevsa.trainingtask.web.service.ServiceException;

/**
 * The default implementation of {@link EmployeeService}
 */
public class DefaultEmployeeService implements EmployeeService {

    private static final Logger LOG = Logger.getLogger(DefaultEmployeeService.class);
    private final EmployeeRepository employeeRepository;

    /**
     * Instantiates a new Default employee com.qulix.losevsa.trainingtask.web.service.
     */
    public DefaultEmployeeService() {
        RepositoryProvider provider = RepositoryProvider.getInstance();
        employeeRepository = provider.getEmployeeRepository();
    }

    @Override
    public Employee createEmployee(String firstName, String surName, String patronymic, String position) {
        try {
            validateValues(firstName, surName, patronymic, position);

            Employee employee = new Employee();
            employee.setFirstName(firstName);
            employee.setSurName(surName);
            employee.setPosition(position);
            if (patronymic != null && !patronymic.isBlank()) {
                employee.setPatronymic(patronymic);
            }

            employee = employeeRepository.saveEmployee(employee);
            LOG.info("Successfully created employee with id " + employee.getId());

            return employee;
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try {
            LOG.info("Getting all employees.");
            return employeeRepository.getAllEmployees();
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Employee getEmployee(long employeeId) {
        try {
            LOG.info("Getting employee with id " + employeeId);
            Employee employee = employeeRepository.getEmployeeById(employeeId);
            if (employee == null) {
                LOG.error("Employee with id " + employeeId + " doesn't exist.");
                throw new ServiceException("Сотрудник с id " + employeeId + " не существует!");
            }

            return employee;
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Employee updateEmployee(long employeeId, String firstName, String surName, String patronymic, String position) {
        try {
            checkThatEmployeeExists(employeeId);
            validateValues(firstName, surName, patronymic, position);

            Employee employee = new Employee();
            employee.setId(employeeId);
            employee.setFirstName(firstName);
            employee.setSurName(surName);
            employee.setPosition(position);
            if (patronymic != null && !patronymic.isBlank()) {
                employee.setPatronymic(patronymic);
            }

            employee = employeeRepository.updateEmployee(employee);
            LOG.info("Successfully updated employee with id " + employee.getId());

            return employee;
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long deleteEmployee(long employeeId) {
        try {
            checkThatEmployeeExists(employeeId);

            employeeId = employeeRepository.deleteEmployeeById(employeeId);
            LOG.info("Successfully deleted employee with id " + employeeId);

            return employeeId;
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void checkThatEmployeeExists(long employeeId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) {
            LOG.error("Employee with id " + employeeId + " doesn't exist.");
            throw new ServiceException("Сотрудник с id " + employeeId + " не существует!");
        }
    }

    private void validateValues(String firstName, String surName, String patronymic, String position) {
        if (firstName == null || firstName.isBlank() || surName == null || surName.isBlank()
            || position == null || position.isBlank()) {
            throw new ServiceException("Введите обязательные поля.");
        }

        if (firstName.length() > 30) {
            throw new ServiceException("Длина имени не больше 30 символов.");
        }

        if (surName.length() > 30) {
            throw new ServiceException("Длина фамилии не больше 30 символов.");
        }

        if (position.length() > 30) {
            throw new ServiceException("Длина должности не больше 30 символов.");
        }

        if (patronymic != null && patronymic.length() > 30) {
            throw new ServiceException("Длина отчества не больше 30 символов.");
        }
    }
}