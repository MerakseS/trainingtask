package com.qulix.losevsa.trainingtask.web.service.impl;

import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.repository.EmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryProvider;
import com.qulix.losevsa.trainingtask.web.service.EmployeeService;
import com.qulix.losevsa.trainingtask.web.service.IncorrectInputException;

/**
 * The default implementation of {@link EmployeeService}
 */
public class DefaultEmployeeService implements EmployeeService {

    private static final Logger LOG = Logger.getLogger(DefaultEmployeeService.class);
    private static final int FIELDS_MAX_LENGTH = 30;
    private final EmployeeRepository employeeRepository;

    /**
     * Instantiates a new Default employee com.qulix.losevsa.trainingtask.web.service.
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
        LOG.info("Successfully created employee with id " + employee.getId());
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
            LOG.error("Employee with id " + employeeId + " doesn't exist.");
            throw new IncorrectInputException("Сотрудник с id " + employeeId + " не существует!");
        }

        LOG.info("Successfully get employee with id " + employeeId);

        return employee;
    }

    @Override
    public void updateEmployee(long employeeId, String firstName, String surName, String patronymic, String position) {
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
    }

    @Override
    public void deleteEmployee(long employeeId) {
        checkThatEmployeeExists(employeeId);

        employeeId = employeeRepository.deleteEmployeeById(employeeId);
        LOG.info("Successfully deleted employee with id " + employeeId);
    }

    private void checkThatEmployeeExists(long employeeId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (employee == null) {
            LOG.error("Employee with id " + employeeId + " doesn't exist.");
            throw new IncorrectInputException("Сотрудник с id " + employeeId + " не существует!");
        }
    }

    private void validateValues(String firstName, String surName, String patronymic, String position) {
        if (firstName == null || firstName.isBlank() || surName == null || surName.isBlank()
            || position == null || position.isBlank()) {
            LOG.warn(format("Required fields are empty. First name: %s, surname: %s, position: %s", firstName, surName, position));
            throw new IncorrectInputException("Введите обязательные поля.");
        }

        if (firstName.length() > FIELDS_MAX_LENGTH) {
            LOG.warn(format("Length of first name is more then %d. First name: %s", FIELDS_MAX_LENGTH, firstName));
            throw new IncorrectInputException(format("Длина имени не больше %d символов.", FIELDS_MAX_LENGTH));
        }

        if (surName.length() > FIELDS_MAX_LENGTH) {
            LOG.warn(format("Length of surname is more then %d. Surname: %s", FIELDS_MAX_LENGTH, surName));
            throw new IncorrectInputException(format("Длина фамилии не больше %d символов.", FIELDS_MAX_LENGTH));
        }

        if (position.length() > FIELDS_MAX_LENGTH) {
            LOG.warn(format("Length of position is more then %d. Position: %s", FIELDS_MAX_LENGTH, position));
            throw new IncorrectInputException(format("Длина должности не больше %d символов.", FIELDS_MAX_LENGTH));
        }

        if (patronymic != null && patronymic.length() > FIELDS_MAX_LENGTH) {
            LOG.warn(format("Length of patronymic is more then %d. Patronymic: %s", FIELDS_MAX_LENGTH, patronymic));
            throw new IncorrectInputException(format("Длина отчества не больше %d символов.", FIELDS_MAX_LENGTH));
        }
    }
}
