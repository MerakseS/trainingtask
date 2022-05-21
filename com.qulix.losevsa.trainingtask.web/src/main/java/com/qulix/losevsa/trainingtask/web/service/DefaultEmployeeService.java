package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.exception.EmployeeFieldLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.PageNotFoundException;

/**
 * The default {@link Employee} implementation of {@link Service}.
 */
public class DefaultEmployeeService implements Service<Employee> {

    private static final Logger LOG = Logger.getLogger(DefaultEmployeeService.class);
    private static final int FIELDS_MAX_LENGTH = 30;
    private final Repository<Employee> employeeRepository;

    /**
     * Instantiates a new Default employee service.
     * @param employeeRepository the repository for {@link Employee}
     */
    public DefaultEmployeeService(Repository<Employee> employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void create(Employee employee) {
        validate(employee);
        employee = employeeRepository.save(employee);
        LOG.info(format("Successfully created employee with id %d", employee.getId()));
    }

    @Override
    public List<Employee> getAll() {
        LOG.info("Getting all employees.");
        return employeeRepository.getAll();
    }

    @Override
    public Employee get(long employeeId) {
        Employee employee = employeeRepository.getById(employeeId);
        if (employee == null) {
            throw new PageNotFoundException(format("Employee with id %d doesn't exist.", employeeId));
        }

        LOG.info(format("Successfully get employee with id %d", employeeId));

        return employee;
    }

    @Override
    public void update(Employee employee) {
        Employee oldEmployee = employeeRepository.getById(employee.getId());
        if (oldEmployee == null) {
            throw new PageNotFoundException(format("Employee with id %d doesn't exist.", employee.getId()));
        }

        validate(employee);
        employee = employeeRepository.update(employee);
        LOG.info(format("Successfully updated employee with id %d", employee.getId()));
    }

    @Override
    public void delete(long employeeId) {
        Employee employee = employeeRepository.getById(employeeId);
        if (employee == null) {
            throw new PageNotFoundException(format("Employee with id %d doesn't exist.", employeeId));
        }

        employeeId = employeeRepository.deleteById(employeeId);
        LOG.info(format("Successfully deleted employee with id %d", employeeId));
    }

    @Override
    public void validate(Employee employee) {
        if (employee.getFirstName() == null || employee.getFirstName().isBlank()
            || employee.getSurname() == null || employee.getSurname().isBlank()
            || employee.getPosition() == null || employee.getPosition().isBlank()) {
            throw new FieldNotFilledException(
                format("Required fields are empty. First name: '%s', surname: '%s', position: '%s'",
                    employee.getFirstName(),
                    employee.getSurname(),
                    employee.getPosition())
            );
        }

        if (employee.getFirstName().length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of first name is more then %d. First name: %s", FIELDS_MAX_LENGTH, employee.getFirstName())
            );
        }

        if (employee.getSurname().length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of surname is more then %d. Surname: %s", FIELDS_MAX_LENGTH, employee.getSurname())
            );
        }

        if (employee.getPosition().length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of position is more then %d. Position: %s", FIELDS_MAX_LENGTH, employee.getPosition())
            );
        }

        if (employee.getPatronymic() != null && employee.getPatronymic().length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of patronymic is more then %d. Patronymic: %s", FIELDS_MAX_LENGTH, employee.getPatronymic())
            );
        }
    }
}
