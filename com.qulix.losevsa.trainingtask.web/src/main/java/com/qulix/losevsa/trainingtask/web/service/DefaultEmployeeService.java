package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.dto.EmployeeDto;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryProvider;
import com.qulix.losevsa.trainingtask.web.service.exception.EmployeeFieldLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * The default {@link Employee} implementation of {@link Service}.
 */
public class DefaultEmployeeService implements Service<Employee, EmployeeDto> {

    private static final Logger LOG = Logger.getLogger(DefaultEmployeeService.class);
    private static final int FIELDS_MAX_LENGTH = 30;
    private final Repository<Employee> employeeRepository;

    /**
     * Instantiates a new Default employee service.
     */
    public DefaultEmployeeService() {
        RepositoryProvider provider = RepositoryProvider.getInstance();
        employeeRepository = provider.getEmployeeRepository();
    }

    @Override
    public void create(EmployeeDto employeeDto) {
        validateValues(employeeDto);

        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setSurname(employeeDto.getSurname());
        employee.setPosition(employeeDto.getPosition());
        if (employeeDto.getPatronymic() != null && !employeeDto.getPatronymic().isBlank()) {
            employee.setPatronymic(employeeDto.getPatronymic());
        }

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
            throw new NotFoundException(format("Employee with id %d doesn't exist.", employeeId));
        }

        LOG.info(format("Successfully get employee with id %d", employeeId));

        return employee;
    }

    @Override
    public void update(long employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.getById(employeeId);
        if (employee == null) {
            throw new NotFoundException(format("Employee with id %d doesn't exist.", employeeId));
        }

        validateValues(employeeDto);

        Employee newEmployee = new Employee();
        newEmployee.setId(employeeId);
        newEmployee.setFirstName(employeeDto.getFirstName());
        newEmployee.setSurname(employeeDto.getSurname());
        newEmployee.setPosition(employeeDto.getPosition());
        if (employeeDto.getPatronymic() != null && !employeeDto.getPatronymic().isBlank()) {
            newEmployee.setPatronymic(employeeDto.getPatronymic());
        }

        newEmployee = employeeRepository.update(newEmployee);
        LOG.info(format("Successfully updated employee with id %d", newEmployee.getId()));
    }

    @Override
    public void delete(long employeeId) {
        Employee employee = employeeRepository.getById(employeeId);
        if (employee == null) {
            throw new NotFoundException(format("Employee with id %d doesn't exist.", employeeId));
        }

        employeeId = employeeRepository.deleteById(employeeId);
        LOG.info(format("Successfully deleted employee with id %d", employeeId));
    }

    private void validateValues(EmployeeDto employeeDto) {
        if (employeeDto.getFirstName() == null || employeeDto.getFirstName().isBlank()
            || employeeDto.getSurname() == null || employeeDto.getSurname().isBlank()
            || employeeDto.getPosition() == null || employeeDto.getPosition().isBlank()) {
            throw new FieldNotFilledException(
                format("Required fields are empty. First name: '%s', surname: '%s', position: '%s'", employeeDto.getFirstName(), employeeDto.getSurname(), employeeDto.getPosition())
            );
        }

        if (employeeDto.getFirstName().length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of first name is more then %d. First name: %s", FIELDS_MAX_LENGTH, employeeDto.getFirstName())
            );
        }

        if (employeeDto.getSurname().length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of surname is more then %d. Surname: %s", FIELDS_MAX_LENGTH, employeeDto.getSurname())
            );
        }

        if (employeeDto.getPosition().length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of position is more then %d. Position: %s", FIELDS_MAX_LENGTH, employeeDto.getPosition())
            );
        }

        if (employeeDto.getPatronymic() != null && employeeDto.getPatronymic().length() > FIELDS_MAX_LENGTH) {
            throw new EmployeeFieldLengthExceededException(
                format("Length of patronymic is more then %d. Patronymic: %s", FIELDS_MAX_LENGTH, employeeDto.getPatronymic())
            );
        }
    }
}
