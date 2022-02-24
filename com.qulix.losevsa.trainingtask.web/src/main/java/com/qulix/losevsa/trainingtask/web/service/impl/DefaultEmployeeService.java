package com.qulix.losevsa.trainingtask.web.service.impl;

import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.repository.EmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryProvider;
import com.qulix.losevsa.trainingtask.web.service.EmployeeService;
import com.qulix.losevsa.trainingtask.web.service.exception.EmployeeFieldLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * The default implementation of {@link EmployeeService}.
 */
public class DefaultEmployeeService implements EmployeeService {

    private static final Logger LOG = Logger.getLogger(DefaultEmployeeService.class);
    private static final int FIELDS_MAX_LENGTH = 30;
    private final EmployeeRepository employeeRepository;

    /**
     * Instantiates a new Default employee service.
     */
    public DefaultEmployeeService() {
        RepositoryProvider provider = RepositoryProvider.getInstance();
        employeeRepository = provider.getEmployeeRepository();
    }

    @Override
    public void createEmployee(String firstName, String surName, String patronymic, String position) {
        validateValues(firstName, surName, patronymic, position);

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setSurName(surName);
        employee.setPosition(position);
        if (patronymic != null && !patronymic.isBlank()) {
            employee.setPatronymic(patronymic);
        }

        employee = employeeRepository.saveEmployee(employee);
        LOG.info(format("Successfully created employee with id %d", employee.getId()));
    }

    @Override
    public List<Employee> getAllEmployees() {
        LOG.info("Getting all employees.");
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Employee getEmployee(long employeeId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) {
            throw new NotFoundException(format("Employee with id %d doesn't exist.", employeeId));
        }

        LOG.info(format("Successfully get employee with id %d", employeeId));

        return employee;
    }

    @Override
    public void updateEmployee(long employeeId, String firstName, String surName, String patronymic, String position) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) {
            throw new NotFoundException(format("Employee with id %d doesn't exist.", employeeId));
        }

        validateValues(firstName, surName, patronymic, position);

        Employee newEmployee = new Employee();
        newEmployee.setId(employeeId);
        newEmployee.setFirstName(firstName);
        newEmployee.setSurName(surName);
        newEmployee.setPosition(position);
        if (patronymic != null && !patronymic.isBlank()) {
            newEmployee.setPatronymic(patronymic);
        }

        newEmployee = employeeRepository.updateEmployee(newEmployee);
        LOG.info(format("Successfully updated employee with id %d", newEmployee.getId()));
    }

    @Override
    public void deleteEmployee(long employeeId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) {
            throw new NotFoundException(format("Employee with id %d doesn't exist.", employeeId));
        }

        employeeId = employeeRepository.deleteEmployeeById(employeeId);
        LOG.info(format("Successfully deleted employee with id %d", employeeId));
    }

    private void validateValues(String firstName, String surName, String patronymic, String position) {
        if (firstName == null || firstName.isBlank() || surName == null || surName.isBlank()
            || position == null || position.isBlank()) {
            throw new FieldNotFilledException(
                format("Required fields are empty. First name: '%s', surname: '%s', position: '%s'", firstName, surName, position)
            );
        }

        if (firstName.length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of first name is more then %d. First name: %s", FIELDS_MAX_LENGTH, firstName)
            );
        }

        if (surName.length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of surname is more then %d. Surname: %s", FIELDS_MAX_LENGTH, surName)
            );
        }

        if (position.length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of position is more then %d. Position: %s", FIELDS_MAX_LENGTH, position)
            );
        }

        if (patronymic != null && patronymic.length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of patronymic is more then %d. Patronymic: %s", FIELDS_MAX_LENGTH, patronymic)
            );
        }
    }
}
